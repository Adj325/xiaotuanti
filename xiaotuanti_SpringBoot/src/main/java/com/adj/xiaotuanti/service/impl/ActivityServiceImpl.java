package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.*;
import com.adj.xiaotuanti.pojo.*;
import com.adj.xiaotuanti.pojo.constants.ActivityStatus;
import com.adj.xiaotuanti.service.ActivityService;
import com.adj.xiaotuanti.util.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    private final UserDAO userDAO;
    private final Relation_Tag_ActivityDAO relationTagActivityDAO;
    private final TagDAO tagDAO;
    private final PartDAO partDAO;
    private final MemberDAO memberDAO;
    private final ActivityDAO activityDAO;



    @Autowired
    public ActivityServiceImpl(UserDAO userDAO, Relation_Tag_ActivityDAO relationTagActivityDAO, TagDAO tagDAO, PartDAO partDAO, MemberDAO memberDAO, ActivityDAO activityDAO) {
        this.userDAO = userDAO;
        this.relationTagActivityDAO = relationTagActivityDAO;
        this.tagDAO = tagDAO;
        this.partDAO = partDAO;
        this.memberDAO = memberDAO;
        this.activityDAO = activityDAO;
    }

    public Activity getActivityById(String activityId) {
        return activityDAO.getActivityById(activityId);
    }

    public void deleteActivity(Activity activity) {
        activityDAO.addActivity(activity);
    }

    public void deleteActivityById(String activityId) {
        activityDAO.deleteActivityById(activityId);
    }

    public List<Activity> getAllActivities() {
        return activityDAO.getAllActivities();
    }

    public List<Activity> getActivitiesUserJoin(User user) {
        return activityDAO.getActivitiesUserJoin(user);
    }

    public List<Activity> getActivitiesUserConduct(User user) {
        return activityDAO.getActivitiesUserConduct(user);
    }

    public List<Activity> getActivitiesFromAllTeams() {
        return activityDAO.getActivitiesFromAllTeams();
    }

    public List<Activity> getActivitiesFromTeamsUserJoin(User user) {
        return activityDAO.getActivitiesFromTeamsUserJoin(user);
    }

    public List<Activity> getActivityByUserAndTeam(Activity activity) {
        return activityDAO.getActivityByUserAndTeam(activity);
    }

    public List<Activity> getActivitiesByTeamId(String teamId) {
        return activityDAO.getActivitiesByTeamId(teamId);
    }

    public List<Activity> getActiveActivitiesByTeamId(String teamId) {
        return activityDAO.getActiveActivitiesByTeamId(teamId);
    }

    public List<Activity> searchActivitiesByKeyword(String keyword) {
        return activityDAO.searchActivitiesByKeyword(WordUtils.getNlpSeg(keyword));
    }

    @Override
    public List<Activity> getActivitiesByOpenAndTeamNotOpen(String userId) {
        return activityDAO.getActivitiesByOpenAndTeamNotOpen(userId);
    }

    @Override
    public Activity getRecommendOfficials(Activity activity) {
        return null;
    }

    public void addActivity(Activity activity) {
        Activity tempActivity = getActivityById(activity.getId());
        if (tempActivity == null) {
            activityDAO.addActivity(activity);
        } else {
            updateActivity(activity);
        }
    }

    @Override
    public List<Activity> getActivitiesByStatusNotBegDateLessThan(ActivityStatus status, Date date) {
        return activityDAO.getActivitiesByStatusNotBegDateLessThan(status, date);
    }

    @Override
    public List<Activity> getActivitiesByBegDate(Date date) {
       return activityDAO.getActivitiesByBegDate(date);
    }

    public void updateActivity(Activity activity) {
        activityDAO.updateActivity(activity);
    }

    // 创建活动：未插入到数据库
    @Transactional
    public void createActivity(Activity activity) {
        activityDAO.addActivity(activity);
    }

    @Override
    public List<Activity> getActivitiesByOpenAndTeamNotOpenAndKeywords(String id, List<String> keywords) {
        return activityDAO.getActivitiesByOpenAndTeamNotOpenAndKeywords(id, keywords);
    }
}