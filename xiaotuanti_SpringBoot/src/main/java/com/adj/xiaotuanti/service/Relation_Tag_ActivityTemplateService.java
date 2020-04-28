package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.Relation_Tag_ActivityTemplate;
import com.adj.xiaotuanti.pojo.Tag;

import java.util.List;

public interface Relation_Tag_ActivityTemplateService {

    void addRelation(Relation_Tag_ActivityTemplate relation);

    void updateRelation(Relation_Tag_ActivityTemplate relation);

    void deleteRelation(Relation_Tag_ActivityTemplate relation);

    void deleteRelationById(String relationId);

    Relation_Tag_ActivityTemplate getRelation(Relation_Tag_ActivityTemplate relation);

    Relation_Tag_ActivityTemplate getRelationById(String relationId);

    List<Relation_Tag_ActivityTemplate> getAllRelations();

    Relation_Tag_ActivityTemplate getRelationByTag(Tag tag);

    List<Relation_Tag_ActivityTemplate> getRelationsByTag(Tag tag);

    void deleteRelationByTag(Tag tag);

    void deleteRelationsByTag(Tag tag);

    Relation_Tag_ActivityTemplate getRelationByActivityTemplate(ActivityTemplate activityTemplate);

    List<Relation_Tag_ActivityTemplate> getRelationsByActivityTemplate(ActivityTemplate activityTemplate);

    void deleteRelationByActivityTemplate(ActivityTemplate activityTemplate);

    void deleteRelationsByActivityTemplate(ActivityTemplate activityTemplate);

}