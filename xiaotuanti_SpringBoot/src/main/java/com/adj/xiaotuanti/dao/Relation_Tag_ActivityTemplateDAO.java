package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.Relation_Tag_ActivityTemplate;
import com.adj.xiaotuanti.pojo.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("Relation_Tag_ActivityTemplateDAO")
public interface Relation_Tag_ActivityTemplateDAO {

    void addRelation(@Param("relation") Relation_Tag_ActivityTemplate Relation);

    void updateRelation(@Param("relation") Relation_Tag_ActivityTemplate Relation);

    void deleteRelation(@Param("relation") Relation_Tag_ActivityTemplate Relation);

    void deleteRelationById(@Param("relationId") String RelationId);

    Relation_Tag_ActivityTemplate getRelation(@Param("relation") Relation_Tag_ActivityTemplate Relation);

    Relation_Tag_ActivityTemplate getRelationById(@Param("relationId") String RelationId);

    List<Relation_Tag_ActivityTemplate> getAllRelations();

    Relation_Tag_ActivityTemplate getRelationByTag(@Param("tag") Tag tag);

    List<Relation_Tag_ActivityTemplate> getRelationsByTag(@Param("tag") Tag tag);

    void deleteRelationByTag(@Param("tag") Tag tag);

    void deleteRelationsByTag(@Param("tag") Tag tag);

    Relation_Tag_ActivityTemplate getRelationByActivityTemplate(@Param("activityTemplate") ActivityTemplate activityTemplate);

    List<Relation_Tag_ActivityTemplate> getRelationsByActivityTemplate(@Param("activityTemplate") ActivityTemplate activityTemplate);

    void deleteRelationByActivityTemplate(@Param("activityTemplate") ActivityTemplate activityTemplate);

    void deleteRelationsByActivityTemplate(@Param("activityTemplate") ActivityTemplate activityTemplate);

    Relation_Tag_ActivityTemplate getRelationByTagIdAndActivityTemplateId(@Param("tagId") String tagId, @Param("activityTemplateId") String activityTemplateId);

    List<Relation_Tag_ActivityTemplate> getRelationsByTagIdAndActivityTemplateId(@Param("tagId") String tagId, @Param("activityTemplateId") String activityTemplateId);

    void deleteRelationByTagIdAndActivityTemplateId(@Param("tagId") String tagId, @Param("activityTemplateId") String activityTemplateId);

    void deleteRelationsByTagIdAndActivityTemplateId(@Param("tagId") String tagId, @Param("activityTemplateId") String activityTemplateId);

}
