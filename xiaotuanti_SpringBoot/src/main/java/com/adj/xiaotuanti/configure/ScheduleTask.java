package com.adj.xiaotuanti.configure;

import com.adj.xiaotuanti.pojo.*;
import com.adj.xiaotuanti.pojo.constants.ActivityStatus;
import com.adj.xiaotuanti.service.ActivityService;
import com.adj.xiaotuanti.service.NoticeService;
import com.adj.xiaotuanti.service.Relation_Activity_UserService;
import com.adj.xiaotuanti.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class ScheduleTask {
    private final ActivityService activityService;
    private final UserService userService;
    private final NoticeService noticeService;
    private final Relation_Activity_UserService recordService;

    @Autowired
    public ScheduleTask(ActivityService activityService, UserService userService, NoticeService noticeService, Relation_Activity_UserService recordService) {
        this.activityService = activityService;
        this.userService = userService;
        this.noticeService = noticeService;
        this.recordService = recordService;
    }

    // 每天00:00运行一遍
    // 作用：改变活动状态，策划中 -> 进行中
    @Scheduled(cron = "0 0 0 * * ?")
    private void changeActivityStatusToRunning() {
        List<Activity> activities = activityService.getActivitiesByBegDate(new Date());
        for (Activity activity : activities) {
            Team ownerTeam = activity.getTeam();
            String activityId = activity.getId();
            List<Part> parts = activity.getParts();
            // 通知本团体的成员
            List<User> teamMemberUserList = userService.getUsersByTeamId(ownerTeam.getId());
            for (User teamMemberUser : teamMemberUserList) {
                Notice notice = new Notice();
                notice.setPostTime(new Date());
                notice.setReceiver(teamMemberUser);
                String content = String.format("你报名参加的由“%s”主办的“%s”活动已经发布了！", activity.getTeam().getName(), activity.getName());
                notice.setContent(content);
                noticeService.addNotice(notice);
            }

            // 保存阶段、通知参与者、添加参与者活动记录
            for (Part part : parts) {
                List<String> officials = new ArrayList<>(part.getOfficialIds());
                for (int i = 0; i < officials.size(); i++) {
                    // 通知参与者
                    String offUserId = officials.get(i);
                    Notice notice = new Notice();
                    User receiver = userService.getUserById(offUserId);
                    notice.setPostTime(new Date());
                    notice.setReceiver(receiver);
                    String content = String.format("你成功参加了由“%s”主办的“%s”活动", activity.getTeam().getName(), activity.getName());
                    notice.setContent(content);
                    noticeService.addNotice(notice);

                    // 添加参与者活动记录
                    Relation_Activity_User record = new Relation_Activity_User(activity, receiver);
                    recordService.addRecord(record);
                }
            }

            // 通知报名者活动发布，及报名结果
            List<User> willingUserList = userService.getWillingUsersByActivity(activity);
            for (User willingUser : willingUserList) {
                // 通知报名者 活动发布
                Notice notice = new Notice();
                notice.setPostTime(new Date());
                notice.setReceiver(willingUser);
                String content = String.format("你报名参加的由“%s”主办的“%s”活动已经发布了！", activity.getTeam().getName(), activity.getName());
                notice.setContent(content);
                noticeService.addNotice(notice);

                // 通知报名者 报名结果
                Integer signResult = recordService.getRecordByActivityIdAndUserId(activityId, willingUser.getId());
                if (signResult == null) {
                    notice.setPostTime(new Date());
                    notice.setReceiver(willingUser);
                    content = String.format("你未能参加的由“%s”主办的“%s”活动", activity.getTeam().getName(), activity.getName());
                    notice.setContent(content);
                    noticeService.addNotice(notice);
                }
            }

            // 活动结束
            activity.setStatus(ActivityStatus.RUNNING);
            activityService.updateActivity(activity);
        }
    }


    // 每天00:00运行一遍
    // 作用：改变活动状态，进行中 -> 已结束
    @Scheduled(cron = "0 0 0 * * ?")
    private void changeActivityStatusToEnd() {
        List<Activity> activities = activityService.getActivitiesByStatusNotBegDateLessThan(ActivityStatus.END, new Date());
        for (Activity activity : activities) {
            // 活动结束
            activity.setStatus(ActivityStatus.END);
            activityService.updateActivity(activity);
        }
    }
}