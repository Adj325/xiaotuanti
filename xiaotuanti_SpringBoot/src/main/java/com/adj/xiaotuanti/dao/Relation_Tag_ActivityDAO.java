package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Relation_Tag_Activity;
import com.adj.xiaotuanti.pojo.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("Relation_Tag_ActivityDAO")
public interface Relation_Tag_ActivityDAO {

    void addRelation(@Param("relation") Relation_Tag_Activity Relation);

    void updateRelation(@Param("relation") Relation_Tag_Activity Relation);

    void deleteRelation(@Param("relation") Relation_Tag_Activity Relation);

    void deleteRelationById(@Param("relationId") String RelationId);

    Relation_Tag_Activity getRelation(@Param("relation") Relation_Tag_Activity Relation);

    Relation_Tag_Activity getRelationById(@Param("relationId") String RelationId);

    List<Relation_Tag_Activity> getAllRelations();

    Relation_Tag_Activity getRelationByTag(@Param("tag") Tag tag);

    List<Relation_Tag_Activity> getRelationsByTag(@Param("tag") Tag tag);

    void deleteRelationByTag(@Param("tag") Tag tag);

    void deleteRelationsByTag(@Param("tag") Tag tag);

    Relation_Tag_Activity getRelationByActivity(@Param("activity") Activity activity);

    List<Relation_Tag_Activity> getRelationsByActivity(@Param("activity") Activity activity);

    void deleteRelationByActivity(@Param("activity") Activity activity);

    void deleteRelationsByActivity(@Param("activity") Activity activity);

    Relation_Tag_Activity getRelationByTagIdAndActivityId(@Param("tagId") String tagId, @Param("activityId") String activityId);

    List<Relation_Tag_Activity> getRelationsByTagIdAndActivityId(@Param("tagId") String tagId, @Param("activityId") String activityId);

    void deleteRelationByTagIdAndActivityId(@Param("tagId") String tagId, @Param("activityId") String activityId);

    void deleteRelationsByTagIdAndActivityId(@Param("tagId") String tagId, @Param("activityId") String activityId);

}
