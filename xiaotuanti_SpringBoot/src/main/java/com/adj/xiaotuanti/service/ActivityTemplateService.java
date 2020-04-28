package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.Team;
import com.adj.xiaotuanti.pojo.User;

import java.util.List;

public interface ActivityTemplateService {

    void addActivityTemplate(ActivityTemplate ActivityTemplate);

    void updateActivityTemplate(ActivityTemplate ActivityTemplate);

    void deleteActivityTemplate(ActivityTemplate ActivityTemplate);

    void deleteActivityTemplateById(Integer ActivityTemplateId);

    ActivityTemplate getActivityTemplate(ActivityTemplate ActivityTemplate);

    ActivityTemplate getActivityTemplateById(Integer ActivityTemplateId);

    List<ActivityTemplate> getAllActivityTemplates();

    ActivityTemplate getActivityTemplateByTeam(Team team);

    List<ActivityTemplate> getActivityTemplatesByTeam(Team team);

    void deleteActivityTemplateByTeam(Team team);

    void deleteActivityTemplatesByTeam(Team team);

    List<ActivityTemplate> getActivityTemplatesByUserId(String id);

    List<ActivityTemplate> getActivityTemplatesByTeamId(String teamId);

    List<ActivityTemplate> getActivityTemplatesByUserIdOrTeamId(String userId, String teamId);

    ActivityTemplate getActivityTemplateByActivityId(String activityId);

    List<ActivityTemplate> getActivityTemplatesByUserIdOrTeamIdOrTagNames(String userId, String teamId, String[] tagNames);

    List<ActivityTemplate> getActivityTemplatesByOpenAndTeamNotOpen(String userId);

    List<ActivityTemplate> getActivityTemplatesByOpenAndTeamNotOpenAndKeywords(String userId, List<String> keywords);
}