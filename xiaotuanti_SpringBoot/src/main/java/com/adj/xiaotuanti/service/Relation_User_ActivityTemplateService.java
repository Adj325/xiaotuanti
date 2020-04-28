package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.Relation_User_ActivityTemplate;
import com.adj.xiaotuanti.pojo.Section;
import com.adj.xiaotuanti.pojo.User;

import java.util.List;

public interface Relation_User_ActivityTemplateService {

    void addRelation(Relation_User_ActivityTemplate relation);

    void updateRelation(Relation_User_ActivityTemplate relation);

    void deleteRelation(Relation_User_ActivityTemplate relation);

    void deleteRelationById(String relationId);

    Relation_User_ActivityTemplate getRelation(Relation_User_ActivityTemplate relation);

    Relation_User_ActivityTemplate getRelationById(String relationId);

    List<Relation_User_ActivityTemplate> getAllRelations();

    Relation_User_ActivityTemplate getRelationByUser(User user);

    List<Relation_User_ActivityTemplate> getRelationsByUser(User user);

    void deleteRelationByUser(User user);

    void deleteRelationsByUser(User user);

    Relation_User_ActivityTemplate getRelationByActivityTemplate(ActivityTemplate activityTemplate);

    List<Relation_User_ActivityTemplate> getRelationsByActivityTemplate(ActivityTemplate activityTemplate);

    void deleteRelationByActivityTemplate(ActivityTemplate activityTemplate);

    void deleteRelationsByActivityTemplate(ActivityTemplate activityTemplate);

}