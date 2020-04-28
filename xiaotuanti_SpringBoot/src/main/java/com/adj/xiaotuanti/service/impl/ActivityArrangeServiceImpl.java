package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.*;
import com.adj.xiaotuanti.pojo.*;
import com.adj.xiaotuanti.pojo.vo.FreedomVo;
import com.adj.xiaotuanti.service.ActivityArrangeService;
import com.adj.xiaotuanti.service.FreedomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service("activityArrangeService")
public class ActivityArrangeServiceImpl implements ActivityArrangeService {
    private final FreedomService freedomService;
    private final UserDAO userDAO;
    private final MemberDAO memberDAO;

    @Autowired
    public ActivityArrangeServiceImpl(FreedomService freedomService, UserDAO userDAO, MemberDAO memberDAO) {
        this.freedomService = freedomService;
        this.userDAO = userDAO;
        this.memberDAO = memberDAO;
    }

    private Activity activity;
    // 活动举行日期的星期几
    private Integer weekDay;
    // 活动参与者
    private List<Participate> participates = new ArrayList<>();
    // 活动参与者的数量
    private int participateNumber;
    // 活动阶段
    private List<Part> parts;
    // 活动阶段的数量
    private int partNumber;
    // 活动阶段的安排状态，true: 安排完成，false：待安排
    private boolean[] partArrangeStatues;
    // 平均活跃度添加后，
    private double averageRecord;
    // key:userId, value:user
    private HashMap<String, Participate> uid_participate_map;

    public Activity getRecommendOfficials(Activity activity) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(activity.getBegTimeDate());
        this.weekDay = (calendar.get(Calendar.DAY_OF_WEEK) - 1) % 7;

        List<User> users = userDAO.getCandidatesByActivity(activity);
        if (CollectionUtils.isEmpty(users) || CollectionUtils.isEmpty(activity.getParts())) {
            return activity;
        }

        for (Part part : activity.getParts()) {
            part.setCandidateIds(new HashSet<>());
            part.setOfficialIds(new HashSet<>());
        }

        Team team = activity.getTeam();
        for (User user : users) {
            Member member = memberDAO.getMemberByUserIdAndTeamId(user.getId(), team.getId());
            Participate participate = new Participate(user, member);
            participate.setFreedomVos(freedomService.constructForUser(user));
            participate.setOfficialPart(null);
            participates.add(participate);
        }
        // 根据参与用户安排活动
        this.activity = activity;
        return manage();
    }

    // 初始化
    private void init() {
        this.parts = activity.getParts();
        this.partNumber = parts.size();
        this.participateNumber = participates.size();
        this.partArrangeStatues = new boolean[partNumber];
        this.uid_participate_map = new HashMap<String, Participate>();

        // 为学生设置工作状态, 统计平均活动次数
        this.averageRecord = 0;
        for (Participate participate : participates) {
            participate.setPartCandidateStatus(new boolean[partNumber]);
            participate.setPartOfficialStatus(new boolean[partNumber]);
            participate.setPartFreeDetails(new int[partNumber][2]);
            this.averageRecord += participate.getRecordNumber();
            this.uid_participate_map.put(participate.getUserId(), participate);
        }
        this.averageRecord /= participateNumber;

        for (int partId = 0; partId < partNumber; partId++) {
            Part curPart = parts.get(partId);
            curPart.setCandidateNumber(0);
            curPart.setOfficialNumber(0);
        }
    }

    // 为活动安排人员
    private Activity manage() {
        // 初始化
        init();

        // 筛选出候选人员
        filterCandidates();

        while (isContinue()) {
            // 更新阶段优先级
            updatePartPriorities();
            // partId, mark
            Part priorPart = getPriorPart();
            if (priorPart == null) {
                break;
            }

            int priorPartNo = priorPart.getOrder();
            System.out.printf("阶段%d最为优先！\n", priorPartNo);
            // 在优先阶段中获取优先成员
            Participate priorityParticipate = getPriorParticipate(priorPartNo);
            // 没有候选学生，此阶段不可被安排！
            if (priorityParticipate == null) {
                partArrangeStatues[priorPartNo] = true;
                System.out.printf("阶段%s，无法安排\n\n", priorPartNo);
            } else {
                priorityParticipate.setOfficialPart(priorPartNo);
                uid_participate_map.put(priorityParticipate.getUserId(), priorityParticipate);
                System.out.printf("阶段%s，安排 %s\n\n", priorPartNo, priorityParticipate.getName());
            }
        }
        activity.setParts(parts);
        return activity;
    }

    // 筛选出候选人员
    private void filterCandidates() {
        // 筛选阶段的候选人员
        for (int partId = 0; partId < partNumber; partId++) {
            Part curPart = parts.get(partId);
            int curPartBegTime = curPart.getBegTime();
            int curPartEndTime = curPart.getEndTime();
            // 遍历学生，筛选候选人员
            for (Participate participate : participates) {
                // 遍历学生该天的空闲时间
                FreedomVo[][] freedoms = participate.getFreedoms();
                if (freedoms == null) {
                    continue;
                }
                for (FreedomVo freedomVo : freedoms[this.weekDay]) {
                    if (freedomVo.getBegTime() <= curPartBegTime && curPartEndTime <= freedomVo.getEndTime()) {
                        // 更新阶段候选人
                        Set<String> candidatesList = curPart.getCandidateIds();
                        candidatesList.add(participate.getUserId());
                        // 具体空闲情况
                        participate.setPartFreeDetails(partId, curPartBegTime - freedomVo.getBegTime(), freedomVo.getEndTime() - curPartEndTime);
                        curPart.setCandidateNumber(curPart.getCandidateNumber() + 1);
                        participate.setPartCandidateStatus(partId, true);
                        break;
                    } else {
                        participate.setPartFreeDetails(partId, curPartBegTime - freedomVo.getBegTime(), freedomVo.getEndTime() - curPartEndTime);
                    }
                }
            }
        }
    }

    /**
     * getPartPriority: 获取优先安排的阶段
     * <p>
     * 影响元素：要求人数 - 已安排人数officialNumber，候选人数candidateNumber, 总人数Members.size()
     * 贪心公式：  要求人数 - 已安排人数
     * -----------------------
     * 候选人数
     */
    private void updatePartPriorities() {
        for (int partId = 0; partId < partNumber; partId++) {
            Part curPart = parts.get(partId);
            int targetNumber = curPart.getTargetNumber();
            int officialNumber = curPart.getOfficialNumber();
            int candidateNumber = curPart.getCandidateNumber();

            // 没有候选人，给予0.0的优先级
            if (candidateNumber == 0) {
                curPart.setPriority(0.0);
                continue;
            }else if (partArrangeStatues[partId]) {
                // 已安排完成 或 无法安排
                curPart.setPriority(0.0);
                continue;
            }
            curPart.setPriority(1.0 + (targetNumber - officialNumber) / (candidateNumber));
        }
    }

    private Part getPriorPart() {

        Part priorPart =  parts.get(0);
        for (int partId = 1; partId < partNumber; partId++) {
            if (priorPart.getPriority() < parts.get(partId).getPriority()) {
                priorPart = parts.get(partId);
            }
        }

        if (priorPart.getPriority() <= 0) {
            System.out.println("没有阶段可安排");
            return null;
        }
        return priorPart;
    }

    /**
     * getMemberPriority: 获取优先安排的学生的id
     * 影响元素：    活跃度
     * ----------- * 年级因素(大于1)
     * 平均活跃度
     */
    private Participate getPriorParticipate(int priorPart) {
        Part curPart = parts.get(priorPart);
        List<String> candidateIds = new ArrayList<String>(curPart.getCandidateIds());
        List<String> candidateIds_after = new ArrayList<String>();

        // 寻找此阶段的候选人
        // 计算优先级
        for (int i = 0; i < candidateIds.size(); i++) {
            String candidateUserId = candidateIds.get(i);
            Participate candidateParticipate = uid_participate_map.get(candidateUserId);
            // 学生未被安排
            if (candidateParticipate.getOfficialPart() == null) {
                if (this.averageRecord == 0.0){
                    candidateParticipate.setPriority(1.0);
                }else {
                    candidateParticipate.setPriority(1.0+ candidateParticipate.getRecordNumber() / this.averageRecord);
                }
                candidateIds_after.add(candidateUserId);
                uid_participate_map.put(candidateUserId, candidateParticipate);
            }
        }
        // 此阶段没有候选人了
        if (CollectionUtils.isEmpty(candidateIds_after)) {
            return null;
        }
        // partId, partPriority
        // 挑选出优先级最高的成员
        Participate priorParticipate = uid_participate_map.get(candidateIds_after.get(0)), tempUser;
        for (int i = 1; i < candidateIds_after.size(); i++) {
            tempUser = uid_participate_map.get(candidateIds_after.get(i));
            if (priorParticipate.getPriority() > tempUser.getPriority()) {
                priorParticipate = tempUser;
            }
        }
        if (priorParticipate.getPriority() <= 0) {
            System.out.println("阶段没有任何可选人员！");
            return null;
        }

        System.out.println();

        // 更新学生状态及此阶段的候选人数，正式人数
        String priorUserId = priorParticipate.getUserId();
        priorParticipate.setOfficialPart(priorPart);
        curPart.getOfficialIds().add(priorUserId);
        priorParticipate.setPartOfficialStatus(priorPart, true);

        // 更新正式人数
        curPart.setOfficialNumber(curPart.getOfficialNumber() + 1);

        // 此阶段已安排足够正式人员，不需要再安排了！
        if (curPart.getTargetNumber().equals(curPart.getOfficialNumber())) {
            partArrangeStatues[priorPart] = true;
            System.out.println("阶段" + priorPart + "已安排足够的人");
        }

        // 其他阶段，含有若含有此学生，则其候选人数-1
        for (int partId = 0; partId < partNumber; partId++) {
            if (partId == priorPart) {
                continue;
            }
            Part otherPart = parts.get(partId);
            Set<String> otherPartCandidateIds = otherPart.getCandidateIds();
            if (otherPartCandidateIds.contains(priorUserId)) {
                otherPart.setCandidateNumber(otherPart.getCandidateNumber() - 1);
            }
        }
        uid_participate_map.put(priorParticipate.getUserId(), priorParticipate);
        return priorParticipate;
    }

    // 是否继续安排
    private boolean isContinue() {
        for (int partId = 0; partId < partNumber; partId++) {
            if (!partArrangeStatues[partId]) {
                return true;
            }
        }
        return false;
    }
}
