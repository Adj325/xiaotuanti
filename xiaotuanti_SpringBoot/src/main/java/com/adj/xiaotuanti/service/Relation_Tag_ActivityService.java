package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Relation_Tag_Activity;
import com.adj.xiaotuanti.pojo.Tag;

import java.util.List;

public interface Relation_Tag_ActivityService {

    void addRelation(Relation_Tag_Activity relation);

    void updateRelation(Relation_Tag_Activity relation);

    void deleteRelation(Relation_Tag_Activity relation);

    void deleteRelationById(String RelationId);

    Relation_Tag_Activity getRelation(Relation_Tag_Activity relation);

    Relation_Tag_Activity getRelationById(String RelationId);

    List<Relation_Tag_Activity> getAllRelations();

    Relation_Tag_Activity getRelationByTag(Tag tag);

    List<Relation_Tag_Activity> getRelationsByTag(Tag tag);

    void deleteRelationByTag(Tag tag);

    void deleteRelationsByTag(Tag tag);

    Relation_Tag_Activity getRelationByActivity(Activity activity);

    List<Relation_Tag_Activity> getRelationsByActivity(Activity activity);

    void deleteRelationByActivity(Activity activity);

    void deleteRelationsByActivity(Activity activity);

}