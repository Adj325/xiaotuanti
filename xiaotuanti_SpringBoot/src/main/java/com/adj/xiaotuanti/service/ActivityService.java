package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.pojo.constants.ActivityStatus;

import java.util.Date;
import java.util.List;

public interface ActivityService {

    // 注意点：成员必须是可创建活动的等级
    // 添加活动到数据库：包含人员安排
    void addActivity(Activity activity);

    void updateActivity(Activity activity);

    // 创建活动：未插入到数据库
    void createActivity(Activity activity);

    void deleteActivity(Activity activity);

    void deleteActivityById(String activityId);

    Activity getActivityById(String activityId);

    List<Activity> getAllActivities();

    List<Activity> getActivityByUserAndTeam(Activity activity);

    List<Activity> getActivitiesUserJoin(User user);

    List<Activity> getActivitiesUserConduct(User user);

    List<Activity> getActivitiesFromAllTeams();

    List<Activity> getActivitiesFromTeamsUserJoin(User user);

    List<Activity> getActivitiesByTeamId(String teamId);

    List<Activity> getActiveActivitiesByTeamId(String teamId);

    List<Activity> searchActivitiesByKeyword(String keyword);

    List<Activity> getActivitiesByOpenAndTeamNotOpen(String id);

    Activity getRecommendOfficials(Activity activity);

    List<Activity> getActivitiesByBegDate(Date date);

    List<Activity> getActivitiesByStatusNotBegDateLessThan(ActivityStatus status, Date date);

    List<Activity> getActivitiesByOpenAndTeamNotOpenAndKeywords(String userId, List<String> keywords);

}
