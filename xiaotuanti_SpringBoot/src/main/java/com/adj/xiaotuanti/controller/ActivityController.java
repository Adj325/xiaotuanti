package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.*;
import com.adj.xiaotuanti.pojo.constants.ActivityStatus;
import com.adj.xiaotuanti.pojo.constants.MemberLevel;
import com.adj.xiaotuanti.pojo.dto.ActivityDto;
import com.adj.xiaotuanti.pojo.vo.FreedomVo;
import com.adj.xiaotuanti.service.*;
import com.adj.xiaotuanti.util.WordUtils;
import com.alibaba.fastjson.JSON;
import org.nlpcn.commons.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ActivityController {

    private final Relation_Tag_ActivityService relationTagActivityService;
    private final Relation_Part_UserService relationPartUserService;
    private final TagService tagService;
    private final PartService partService;
    private final UserService userService;
    private final TeamService teamService;
    private final Relation_Activity_UserService relationActivityUserService;
    private final ExperienceService experienceService;
    private final CommentService commentService;
    private final MemberService memberService;
    private final WillingService willingService;
    private final ActivityService activityService;
    private final ActivityArrangeService activityArrangeService;
    private final ActivityTemplateService activityTemplateService;
    private final FreedomService freedomService;

    @Autowired
    public ActivityController(Relation_Tag_ActivityService relationTagActivityService, Relation_Part_UserService relationPartUserService, TagService tagService, PartService partService, UserService userService, TeamService teamService, Relation_Activity_UserService relationActivityUserService, NoticeService noticeService, ExperienceService experienceService, CommentService commentService, MemberService memberService, WillingService willingService, ActivityService activityService, ActivityArrangeService activityArrangeService, ActivityTemplateService activityTemplateService, FreedomService freedomService) {
        this.relationTagActivityService = relationTagActivityService;
        this.relationPartUserService = relationPartUserService;
        this.tagService = tagService;
        this.partService = partService;
        this.userService = userService;
        this.teamService = teamService;
        this.relationActivityUserService = relationActivityUserService;
        this.experienceService = experienceService;
        this.memberService = memberService;
        this.commentService = commentService;
        this.willingService = willingService;
        this.activityService = activityService;
        this.activityArrangeService = activityArrangeService;
        this.activityTemplateService = activityTemplateService;
        this.freedomService = freedomService;
    }

    private HashMap<String, Object> toMap(Activity activity) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", activity.getId());
        ActivityTemplate activityTemplate = activity.getSourceActivityTemplate();
        if (activityTemplate != null) {
            hashMap.put("activityTemplateId", activityTemplate.getId());
        }
        hashMap.put("name", activity.getName());
        hashMap.put("begTime", activity.getBegTime());
        hashMap.put("endTime", activity.getEndTime());
        hashMap.put("introduction", activity.getIntroduction());
        hashMap.put("teamId", activity.getTeam().getId());
        hashMap.put("teamName", activity.getTeam().getName());
        hashMap.put("officialNumber", activity.getOfficialNumber());
        hashMap.put("willingNumber", activity.getWillingNumber());
        hashMap.put("status", activity.getStatus());
        hashMap.put("open", activity.getOpen());
        hashMap.put("parts", activity.getParts());
        hashMap.put("tags", activity.getTags());
        return hashMap;
    }

    private HashMap<String, Object>[] toMapArray(List<Activity> activityList) {
        HashMap<String, Object>[] hashMaps = new HashMap[activityList.size()];
        for (int index = 0; index < activityList.size(); index++) {
            Activity activity = activityList.get(index);
            hashMaps[index] = toMap(activity);
        }
        return hashMaps;
    }

    // 创建活动
    @PostMapping(value = "api/activities")
    public HashMap createActivity(
            @RequestAttribute User user,
            @RequestBody ActivityDto activityDto) {
        String teamId = activityDto.getTeamId();
        HashMap<String, Object> result = new HashMap<>();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        Activity activity = new Activity(activityDto);
        if (activityDto.getActivityTemplateId() != null) {
            activity.setSourceActivityTemplate(activityTemplateService.getActivityTemplateById(activityDto.getActivityTemplateId()));
        }
        activity.setTeam(teamService.getTeamById(teamId));
        activity.setUserConductor(user);
        activity.setMemberConductor(member);
        activity.setStatus(ActivityStatus.PLANNING);
        activityService.createActivity(activity);

        for (Part part : activity.getParts()) {
            part.setActivity(activity);
            partService.addPart(part);
        }
        for (Tag tag : activity.getTags()) {
            tag = tagService.getTagByName(tag.getName());
            if (tag == null) {
                tagService.addTag(tag);
            }
            relationTagActivityService.addRelation(new Relation_Tag_Activity(tag, activity));
        }

        HashMap activityMap = toMap(activity);
        result.put("activity", activityMap);
        result.put("code", 1);
        result.put("msg", "创建成功");
        return result;
    }

    @Transactional
    @PutMapping(value = "api/activities/{activityId}")
    public HashMap updateActivity(
            @RequestAttribute User user,
            @PathVariable String activityId,
            @RequestBody ActivityDto activityDto) {
        Activity oldActivity = activityService.getActivityById(activityId);
        Team ownerTeam = oldActivity.getTeam();
        HashMap<String, Object> result = new HashMap<>();
        // 权限检测
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), ownerTeam.getId());
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            Activity activity = new Activity(activityDto);
            activity.setId(oldActivity.getId());
            if (activityDto.getActivityTemplateId() != null) {
                activity.setSourceActivityTemplate(activityTemplateService.getActivityTemplateById(activityDto.getActivityTemplateId()));
            }
            activity.setTeam(teamService.getTeamById(ownerTeam.getId()));
            activity.setUserConductor(user);
            activity.setMemberConductor(member);
            activityService.updateActivity(activity);
            // 删除标签联系
            relationTagActivityService.deleteRelationByActivity(activity);
            // 新建标签联系
            for (String tagName : activityDto.getTagString().split("#")) {
                Tag tag = tagService.getTagByName(tagName);
                if (tag == null) {
                    tagService.addTag(tag);
                }
                relationTagActivityService.addRelation(new Relation_Tag_Activity(tag, activity));
            }

            // 删除旧阶段
            partService.deletePartByActivityId(activityId);
            if (!CollectionUtils.isEmpty(activity.getParts())) {
                for (Part part : activity.getParts()) {
                    relationPartUserService.deleteRelationsByPart(part);
                }
            }
            // 新建阶段
            if (!CollectionUtils.isEmpty(activityDto.getParts())) {
                int order = 0;
                for (Part part : activityDto.getParts()) {
                    part.setActivity(activity);
                    part.setOrder(order++);
                    partService.addPart(part);
                    for (String userId : part.getOfficialIds()) {
                        relationPartUserService.addRelation(new Relation_Part_User(part, new User(userId)));
                    }
                }
            }
            result.put("msg", "保存成功");
            result.put("code", 1);
        } else {
            result.put("msg", "保存失败");
            result.put("code", -1);
        }
        return result;
    }

    @GetMapping(value = "api/activities/{activityId}/recommendOfficials_candidates")
    public HashMap getActivityRecommendOfficialsAndCandidates(
            @RequestAttribute User user,
            @PathVariable("activityId") String activityId) {
        HashMap<String, Object> result = new HashMap<>();
        Activity activity = activityService.getActivityById(activityId);
        if (activity == null) {
            result.put("code", -1);
            result.put("msg", "没有该活动");
            return result;
        }
        Team ownerTeam = activity.getTeam();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), ownerTeam.getId());
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            activity = activityArrangeService.getRecommendOfficials(activity);
            result.put("data", activity.getParts());
            result.put("code", 1);
            result.put("msg", "获取成功");
            return result;
        } else {
            result.put("code", -1);
            result.put("msg", "无权获取");
            return result;
        }
    }

    @GetMapping(value = "api/activities/{activityId}")
    public HashMap getActivity(
            @RequestAttribute User user,
            @RequestParam("type") String type,
            @PathVariable("activityId") String activityId) {
        HashMap<String, Object> result = new HashMap<>();
        if (type == null || type.length() == 0) {
            result.put("code", -1);
            result.put("msg", "type 无效");
            return result;
        }
        switch (type) {
            case "normal":
                return getActivityInNormalModeByActivityId(user, activityId);
            case "planning":
                return getActivityInPlanningModeByActivityId(user, activityId);
            default:
                return null;
        }
    }

    // 更新活动 - 根据activityId
    @RequestMapping(value = "api/activities/{activityId}")
    public HashMap updateActivity(
            @RequestAttribute User user,
            @RequestParam("teamId") String teamId,
            @PathVariable("activityId") String activityId,
            @RequestBody ActivityDto activityDto) {
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        HashMap<String, Object> result = new HashMap<>();
        // 权限判断
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            Activity activity = new Activity(activityDto);
            activity.setId(activityId);
            activityService.updateActivity(activity);
            result.put("code", 1);
            result.put("msg", "成功更新");
        } else {
            result.put("code", -1);
            result.put("msg", "无权更新");
        }
        return result;
    }

    @Transactional
    @PostMapping(value = "api/activities/delete/{activityId}")
    public HashMap deleteActivity(
            @RequestAttribute User user,
            @PathVariable("activityId") String activityId) {
        HashMap<String, Object> result = new HashMap<>();
        Activity activity = activityService.getActivityById(activityId);
        if (activity == null) {
            result.put("code", -1);
            result.put("msg", "活动不存在");
            return result;
        }
        if (activity.getStatus() == ActivityStatus.END) {
            result.put("code", -1);
            result.put("msg", "活动已完成不可删除");
            return result;
        }
        Team ownerTeam = activity.getTeam();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), ownerTeam.getId());
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
//            // 删除记录
//            relationActivityUserService.deleteRecordByActivityId(activityId);
//            // 删除评论
//            commentService.deleteCommentByActivityId(activityId);
//            // 删除阶段
//            partService.deletePartByActivityId(activityId);
//            // 删除报名
//            willingService.deleteWillingByActivityId(activityId);
            // 删除活动
            activityService.deleteActivityById(activityId);

            result.put("permission", member.getLevel().ordinal());
            result.put("code", 1);
            result.put("msg", "删除成功！");
        } else {
            result.put("permission", -1);
            result.put("code", -1);
            result.put("msg", "无权删除！");
        }
        return result;
    }

    @GetMapping(value = "api/activities")
    public HashMap getActivities(
            @RequestAttribute User user,
            @RequestParam("type") String type,
            @RequestParam(value = "teamId", required = false) String teamId,
            @RequestParam(value = "keyword", required = false) String keyword) {
        HashMap<String, Object> result = new HashMap<>();
        if (type == null || type.length() == 0) {
            result.put("code", "-1");
            result.put("msg", "type 无效");
            return result;
        }
        switch (type) {
            // 团体: 成员 搜索活动
            case "keyword":
                return getActivitiesByKeywordAndTeamId(user, teamId, keyword);
            // 用户
            case "userJoin":
                return getActivitiesUserJoin(user);
            // 用户
            case "userConduct":
                return getActivitiesUserConduct(user);
            case "allTeams":
                return getActivitiesFromAllTeams();
            case "allTeamsUserJoin":
                return getActivitiesFromTeamsUserJoin(user);
            case "active":
                return getActiveActivitiesByTeamId(user, teamId);
            case "teamId":
                return getActivitiesByTeamId(user, teamId);
            case "public":
                return getPublicActivities(user, keyword);
            case "user":
                return getActivitiesByUser(user);
            default:
                return null;
        }
    }

    private HashMap getActivitiesByUser(User user) {
        List<Activity> join = activityService.getActivitiesUserJoin(user);
        List<Activity> conduct = activityService.getActivitiesUserConduct(user);
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "1");
        result.put("msg", "获取成功");
        result.put("conduct", toMapArray(conduct));
        result.put("join", toMapArray(join));
        return result;
    }


    // 根据用户id，获取本团所有活动及其他团体的开放活动
    private HashMap getPublicActivities(User user, String keyword) {
        HashMap<String, Object> result = new HashMap<>();
        List<Activity> activityList;
        if (StringUtil.isNotBlank(keyword)) {
            List<String> keywords = WordUtils.getNlpSeg(keyword);
            activityList = activityService.getActivitiesByOpenAndTeamNotOpenAndKeywords(user.getId(), keywords);
        } else {
            activityList = activityService.getActivitiesByOpenAndTeamNotOpen(user.getId());
        }
        Map[] dataMap = toMapArray(activityList);
        result.put("data", dataMap);
        return result;
    }

    // 团体: 成员 搜索活动
    private HashMap getActivitiesByKeywordAndTeamId(User user, String teamId, String keyword) {
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("data", toMapArray(activityService.searchActivitiesByKeyword(keyword)));
        result.put("permission", member);
        return result;
    }

    // 团体: 获取团体的所有进行中的活动
    private HashMap getActiveActivitiesByTeamId(User user, String teamId) {
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        HashMap<String, Object> result = new HashMap<>();
        List<Activity> activityList = activityService.getActiveActivitiesByTeamId(teamId);
        Map[] dataMap = toMapArray(activityList);
        result.put("data", dataMap);
        if (member != null) {
            result.put("permission", member.getLevel().ordinal());
        } else {
            result.put("permission", -1);
        }
        return result;
    }

    // 团体: 获取团体的所有活动，成员可以获取志愿活动及工作，访客仅可获取志愿活动
    private HashMap getActivitiesByTeamId(User user, String teamId) {
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        HashMap<String, Object> result = new HashMap<>();
        List<Activity> activityList = activityService.getActivitiesByTeamId(teamId);
        if (member != null) {
            //Map[] dataMap = toMapArray(activityList);
            List<Activity> activitiesPlanning = new ArrayList<>();
            List<Activity> activitiesRunning = new ArrayList<>();
            List<Activity> activitiesEnd = new ArrayList<>();
            for (Activity activity : activityList) {
                if (activity.getStatus().equals(ActivityStatus.PLANNING)) {
                    activitiesPlanning.add(activity);
                } else if (activity.getStatus().equals(ActivityStatus.RUNNING)) {
                    activitiesRunning.add(activity);
                } else if (activity.getStatus().equals(ActivityStatus.END)) {
                    activitiesEnd.add(activity);
                }
            }
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("planning", toMapArray(activitiesPlanning));
            dataMap.put("running", toMapArray(activitiesRunning));
            dataMap.put("end", toMapArray(activitiesEnd));
            result.put("data", dataMap);
            result.put("permission", member.getLevel().ordinal());
        } else {
            // todo 有待修改
            // 不是成员筛选活动
            List<Activity> activityList1 = new LinkedList<>();
            for (Activity activity : activityList) {
//                if (activity.getType() == 1)
//                    activityList1.add(activity);
            }
            Map[] dataMap = toMapArray(activityList1);
            result.put("data", dataMap);
            result.put("permission", -1);
        }
        return result;
    }

    // 发现: 所有志愿活动的活动
    private HashMap getActivitiesFromAllTeams() {
        List<Activity> activityList = activityService.getActivitiesFromAllTeams();
        HashMap<String, Object> result = new HashMap<>();
        Map[] dataMap = toMapArray(activityList);
        result.put("code", 1);
        result.put("msg", "获取成功");
        result.put("data", dataMap);
        return result;
    }

    // 发现: 最近活动-用户加入的团体举行中的工作/活动
    private HashMap getActivitiesFromTeamsUserJoin(User user) {
        List<Activity> activityList = activityService.getActivitiesFromTeamsUserJoin(user);
        HashMap<String, Object> result = new HashMap<>();
        Map[] dataMap = toMapArray(activityList);
        result.put("code", 1);
        result.put("msg", "获取成功");
        result.put("data", dataMap);
        return result;
    }

    // 用户: 用户参与的活动
    private HashMap getActivitiesUserJoin(User user) {
        List<Activity> activityList = activityService.getActivitiesUserJoin(user);
        HashMap<String, Object> result = new HashMap<>();
        Map[] dataMap = toMapArray(activityList);
        result.put("code", 1);
        result.put("msg", "获取成功");
        result.put("data", dataMap);
        return result;
    }

    // 用户: 用户创建的活动
    private HashMap getActivitiesUserConduct(User user) {
        List<Activity> activityList = activityService.getActivitiesUserConduct(user);
        HashMap<String, Object> result = new HashMap<>();
        Map[] dataMap = toMapArray(activityList);
        result.put("code", 1);
        result.put("msg", "获取成功");
        result.put("data", dataMap);
        return result;
    }

    // ActivityId: 成员可以获取志愿活动及工作，访客仅可获取志愿活动
    private HashMap getActivityInNormalModeByActivityId(User user, String activityId) {
        Activity activity = activityService.getActivityById(activityId);
        Team ownerTeam = activity.getTeam();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), ownerTeam.getId());
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> dataMap = toMap(activity);
        if (member == null) {
            if (activity.getOpen()) {
                result.put("data", dataMap);
                result.put("experience", null);
                result.put("permission", -1);
                result.put("code", 1);
                result.put("msg", "获取成功！");
            } else {
                result.put("data", "");
                result.put("permission", -1);
                result.put("code", -1);
                result.put("msg", "无权获取！");
            }
        } else if (member.getLevel().ordinal() >= MemberLevel.TEAM_MEMBER.ordinal()) {
            // 普通活动数据
            Map activityMap = toMap(activity);
            List<User> userList = userService.getCandidatesByActivity(activity);
            List<Part> parts = partService.getPartsByActivityId(activityId);
            // 重新筛选候选人，并检测正式人员是否正确(有空)
            Map<String, Object>[] partsMap = new HashMap[parts.size()];
            for (int partId = 0; partId < parts.size(); partId++) {
                Part part = parts.get(partId);
                partsMap[partId] = new HashMap<String, Object>();
                partsMap[partId].put("id", part.getId());
                partsMap[partId].put("order", part.getOrder());
                partsMap[partId].put("begTime", part.getBegTimeStr());
                partsMap[partId].put("endTime", part.getEndTimeStr());
                partsMap[partId].put("content", part.getContent());
                partsMap[partId].put("officials", part.getOfficialIds());
            }
            result.put("parts", partsMap);
            HashMap<String, Object> usersHashMap = new HashMap<>();
            // 补充部门信息及意愿信息
            for (int i = 0; i < userList.size(); i++) {
                HashMap<String, Object> userHashMap = new HashMap<>();
                User tempUser = userList.get(i);
                userHashMap.put("name", tempUser.getRealName());
                usersHashMap.put(tempUser.getId(), userHashMap);
            }
            result.put("activity", activityMap);
            Experience experience = experienceService.getExperienceByActivityId(activityId);
            result.put("experience", experience);
            result.put("permission", member.getLevel().ordinal());
            result.put("code", 1);
            result.put("msg", "获取成功！");
            result.put("hasTemplate", activityTemplateService.getActivityTemplateByActivityId(activityId) != null);
            result.put("userDi", usersHashMap);
        }
        return result;
    }

    // ActivityId: 管理员 获取活动，以安排活动的模式
    private HashMap getActivityInPlanningModeByActivityId(User user, String activityId) {
        HashMap<String, Object> result = new HashMap<>();
        Activity activity = activityService.getActivityById(activityId);
        if (activity == null) {
            result.put("code", -1);
            result.put("msg", "没有该活动");
            return result;
        }
        Team ownerTeam = activity.getTeam();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), ownerTeam.getId());

        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            // 普通活动数据
            Map activityMap = toMap(activity);

            HashMap<String, Participate> participateHashMap = new HashMap<>();
            List<Part> parts = activity.getParts();
            List<User> userList = userService.getCandidatesByActivity(activity);


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(activity.getBegTimeDate());
            Integer weekDay = (calendar.get(Calendar.DAY_OF_WEEK) - 1) % 7;

            for (int partId = 0; partId < parts.size(); partId++) {
                Part part = parts.get(partId);
                part.setCandidateIds(new HashSet<>());
                parts.set(partId, part);
            }
            // 补充部门信息及意愿信息
            for (int i = 0; i < userList.size(); i++) {
                User tempUser = userList.get(i);
                // 安排模式，补全信息
                Member tempMember = memberService.getMemberByUserAndTeam(tempUser, ownerTeam);
                Participate participate = new Participate(tempUser, tempMember);

                Willing willing = willingService.getWillingByUserIdAndActivityId(tempUser.getId(), activityId);
                if (willing == null) {
                    participate.setWilling(null);
                } else {
                    if (willing.getType() == 0) {
                        participate.setWilling(false);
                    } else {
                        participate.setWilling(true);
                    }
                }
                participate.setOfficialPart(-1);

                participate.setFreedomVos(freedomService.constructForUser(tempUser));
                participate.setPartCandidateStatus(new boolean[parts.size()]);
                participate.setPartOfficialStatus(new boolean[parts.size()]);
                participate.setPartFreeDetails(new int[parts.size()][2]);

                for (int partId = 0; partId < parts.size(); partId++) {
                    Part part = parts.get(partId);
                    Integer partBegTime = part.getBegTime();
                    Integer partEndTime = part.getEndTime();
                    // 正式
                    if (part.getOfficialIds() != null && part.getOfficialIds().contains(tempUser.getId())) {
                        participate.setOfficialPart(partId);
                        participate.setPartOfficialStatus(partId, true);
                    }

                    // 遍历学生该天的空闲时间
                    FreedomVo[][] freedoms = participate.getFreedoms();
                    if (freedoms == null) {
                        continue;
                    }
                    for (FreedomVo freedomVo : freedoms[weekDay]) {
                        if (freedomVo.getBegTime() <= partBegTime && partEndTime <= freedomVo.getEndTime()) {
                            // 更新阶段候选人
                            //System.out.println(participate.getName() + "-" + participate.getUserId() + "-候选");

//                            System.out.println("partId: "+partId);
//                            System.out.println("添加前，candidateIds: "+ JSON.toJSONString(part.getCandidateIds()));
                            Set<String> candidatesList = part.getCandidateIds();
                            candidatesList.add(participate.getUserId());
                            part.setCandidateIds(candidatesList);
                            // 具体空闲情况
                            participate.setPartFreeDetails(partId, partBegTime - freedomVo.getBegTime(), freedomVo.getEndTime() - partEndTime);
                            participate.setPartCandidateStatus(partId, true);
//                            System.out.println("添加后，candidateIds: "+ JSON.toJSONString(part.getCandidateIds()));
                            break;
                        } else {
                            participate.setPartFreeDetails(partId, partBegTime - freedomVo.getBegTime(), freedomVo.getEndTime() - partEndTime);
                        }
                    }

                    parts.set(partId, part);
                }
                participateHashMap.put(tempUser.getId(), participate);
            }
            activity.setParts(parts);
            result.put("activity", activityMap);
            result.put("permission", member.getLevel().ordinal());
            result.put("code", 1);
            result.put("msg", "获取成功！");

            result.put("participateDict", participateHashMap);
        } else {
            result.put("code", -1);
            result.put("msg", "无权获取(planning)");
        }
        return result;
    }

}
