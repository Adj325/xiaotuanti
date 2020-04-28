package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.pojo.constants.ActivityStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("ActivityDAO")
public interface ActivityDAO {

    void addActivity(@Param("activity") Activity activity);

    void updateActivity(@Param("activity") Activity activity);

    void deleteActivity(@Param("activity") Activity activity);

    void deleteActivityById(@Param("activityId") String activityId);

    Activity getActivityById(@Param("activityId") String activityId);

    List<Activity> getAllActivities();

    List<Activity> getActivitiesByTeamId(@Param("teamId") String teamId);

    List<Activity> getActiveActivitiesByTeamId(@Param("teamId") String teamId);

    List<Activity> getActivitiesUserJoin(@Param("user") User user);

    List<Activity> getActivitiesUserConduct(@Param("user") User user);

    List<Activity> getActivitiesFromAllTeams();

    List<Activity> getActivitiesFromTeamsUserJoin(@Param("user") User user);

    List<Activity> getActivityByUserAndTeam(@Param("activity") Activity activity);

    List<Activity> searchActivitiesByKeyword(@Param("keywords") List<String> keywords);

    List<Activity> getActivitiesByOpenAndTeamNotOpen(@Param("userId") String userId);

    List<Activity> getActivitiesByBegDate(Date date);

    List<Activity> getActivitiesByStatusNotBegDateLessThan(@Param("status") ActivityStatus status, @Param("date") Date date);

    List<Activity> getActivitiesByOpenAndTeamNotOpenAndKeywords(@Param("userId") String userId, @Param("keywords") List<String> keywords);
}
