package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.ActivityTemplateDAO;
import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.Team;
import com.adj.xiaotuanti.service.ActivityTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ActivityTemplateService")
public class ActivityTemplateServiceImpl implements ActivityTemplateService {

    @Autowired
    private ActivityTemplateDAO activityTemplateDAO;

    public void addActivityTemplate(ActivityTemplate activityTemplate) {
        activityTemplateDAO.addActivityTemplate(activityTemplate);
    }

    public void updateActivityTemplate(ActivityTemplate activityTemplate) {
        activityTemplateDAO.updateActivityTemplate(activityTemplate);
    }

    public void deleteActivityTemplate(ActivityTemplate activityTemplate) {
        activityTemplateDAO.deleteActivityTemplate(activityTemplate);
    }

    public void deleteActivityTemplateById(Integer activityTemplateId) {
        activityTemplateDAO.deleteActivityTemplateById(activityTemplateId);
    }

    public ActivityTemplate getActivityTemplate(ActivityTemplate activityTemplate) {
        return activityTemplateDAO.getActivityTemplate(activityTemplate);
    }

    public ActivityTemplate getActivityTemplateById(Integer activityTemplateId) {
        return activityTemplateDAO.getActivityTemplateById(activityTemplateId);
    }

    public List<ActivityTemplate> getAllActivityTemplates() {
        return activityTemplateDAO.getAllActivityTemplates();
    }

    public ActivityTemplate getActivityTemplateByTeam(Team team) {
        return activityTemplateDAO.getActivityTemplateByTeam(team);
    }

    public List<ActivityTemplate> getActivityTemplatesByTeam(Team team) {
        return activityTemplateDAO.getActivityTemplatesByTeam(team);
    }

    public void deleteActivityTemplateByTeam(Team team) {
        activityTemplateDAO.getActivityTemplateByTeam(team);
    }

    public void deleteActivityTemplatesByTeam(Team team) {
        activityTemplateDAO.getActivityTemplatesByTeam(team);
    }

    @Override
    public List<ActivityTemplate> getActivityTemplatesByUserId(String userId) {
        return activityTemplateDAO.getActivityTemplatesByUserId(userId);
    }

    @Override
    public List<ActivityTemplate> getActivityTemplatesByTeamId(String teamId) {
        return activityTemplateDAO.getActivityTemplatesByTeamId(teamId);
    }

    @Override
    public List<ActivityTemplate> getActivityTemplatesByUserIdOrTeamId(String userId, String teamId) {
        return activityTemplateDAO.getActivityTemplatesByUserIdOrTeamId(userId, teamId);
    }

    @Override
    public ActivityTemplate getActivityTemplateByActivityId(String activityId) {
        return activityTemplateDAO.getActivityTemplateByActivityId(activityId);
    }

    @Override
    public List<ActivityTemplate> getActivityTemplatesByUserIdOrTeamIdOrTagNames(String userId, String teamId, String[] tagNames) {
        return activityTemplateDAO.getActivityTemplatesByUserIdOrTeamIdOrTagNames(userId, teamId, tagNames);
    }

    @Override
    public List<ActivityTemplate> getActivityTemplatesByOpenAndTeamNotOpen(String userId) {
        return activityTemplateDAO.getActivityTemplatesByOpenAndTeamNotOpen(userId);
    }

    @Override
    public List<ActivityTemplate> getActivityTemplatesByOpenAndTeamNotOpenAndKeywords(String userId, List<String> keywords) {
        return activityTemplateDAO.getActivityTemplatesByOpenAndTeamNotOpenAndKeywords(userId, keywords);
    }
}