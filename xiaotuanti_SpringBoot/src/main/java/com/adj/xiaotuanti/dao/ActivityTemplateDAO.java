package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.Team;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ActivityTemplateDAO")
public interface ActivityTemplateDAO {

    void addActivityTemplate(@Param("activityTemplate") ActivityTemplate ActivityTemplate);

    void updateActivityTemplate(@Param("activityTemplate") ActivityTemplate ActivityTemplate);

    void deleteActivityTemplate(@Param("activityTemplate") ActivityTemplate ActivityTemplate);

    void deleteActivityTemplateById(@Param("activityTemplateId") Integer activityTemplateId);

    ActivityTemplate getActivityTemplate(@Param("activityTemplate") ActivityTemplate ActivityTemplate);

    ActivityTemplate getActivityTemplateById(@Param("activityTemplateId") Integer activityTemplateId);

    List<ActivityTemplate> getAllActivityTemplates();

    ActivityTemplate getActivityTemplateByTeam(@Param("team") Team team);

    List<ActivityTemplate> getActivityTemplatesByTeam(@Param("team") Team team);

    void deleteActivityTemplateByTeam(@Param("team") Team team);

    void deleteActivityTemplatesByTeam(@Param("team") Team team);

    ActivityTemplate getActivityTemplateByTeamId(@Param("teamId") Integer teamId);

    List<ActivityTemplate> getActivityTemplatesByTeamId(@Param("teamId") String teamId);

    void deleteActivityTemplateByTeamId(@Param("teamId") Integer teamId);

    void deleteActivityTemplatesByTeamId(@Param("teamId") Integer teamId);

    List<ActivityTemplate> getActivityTemplatesByUserId(String userId);

    List<ActivityTemplate> getActivityTemplatesByUserIdOrTeamId(String userId, String teamId);

    ActivityTemplate getActivityTemplateByActivityId(String activityId);

    List<ActivityTemplate> getActivityTemplatesByUserIdOrTeamIdOrTagNames(@Param("userId") String userId, @Param("teamId") String teamId, @Param("tagNames") String[] tagNames);

    List<ActivityTemplate> getActivityTemplatesByOpenAndTeamNotOpenAndKeywords(@Param("userId") String userId, @Param("keywords") List<String> keywords);

    List<ActivityTemplate> getActivityTemplatesByOpenAndTeamNotOpen(@Param("userId") String userId);
}
