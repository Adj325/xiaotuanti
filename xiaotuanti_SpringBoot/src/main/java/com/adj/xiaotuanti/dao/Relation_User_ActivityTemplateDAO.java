package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.Relation_User_ActivityTemplate;
import com.adj.xiaotuanti.pojo.Section;
import com.adj.xiaotuanti.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("Relation_User_ActivityTemplateDAO")
public interface Relation_User_ActivityTemplateDAO {

    void addRelation(@Param("relation") Relation_User_ActivityTemplate Relation);

    void updateRelation(@Param("relation") Relation_User_ActivityTemplate Relation);

    void deleteRelation(@Param("relation") Relation_User_ActivityTemplate Relation);

    void deleteRelationById(@Param("relationId") String RelationId);

    Relation_User_ActivityTemplate getRelation(@Param("relation") Relation_User_ActivityTemplate Relation);

    Relation_User_ActivityTemplate getRelationById(@Param("relationId") String RelationId);

    List<Relation_User_ActivityTemplate> getAllRelations();

    Relation_User_ActivityTemplate getRelationByUser(@Param("user") User user);

    List<Relation_User_ActivityTemplate> getRelationsByUser(@Param("user") User user);

    void deleteRelationByUser(@Param("user") User user);

    void deleteRelationsByUser(@Param("user") User user);

    Relation_User_ActivityTemplate getRelationByActivityTemplate(@Param("activityTemplate") ActivityTemplate activityTemplate);

    List<Relation_User_ActivityTemplate> getRelationsByActivityTemplate(@Param("activityTemplate") ActivityTemplate activityTemplate);

    void deleteRelationByActivityTemplate(@Param("activityTemplate") ActivityTemplate activityTemplate);

    void deleteRelationsByActivityTemplate(@Param("activityTemplate") ActivityTemplate activityTemplate);

    Relation_User_ActivityTemplate getRelationByUserIdAndActivityTemplateId(@Param("userId") String userId, @Param("activityTemplateId") String activityTemplateId);

    List<Relation_User_ActivityTemplate> getRelationsByUserIdAndActivityTemplateId(@Param("userId") String userId, @Param("activityTemplateId") String activityTemplateId);

    void deleteRelationByUserIdAndActivityTemplateId(@Param("userId") String userId, @Param("activityTemplateId") String activityTemplateId);

    void deleteRelationsByUserIdAndActivityTemplateId(@Param("userId") String userId, @Param("activityTemplateId") String activityTemplateId);

    Integer countByActivityTemplateId(@Param("activityTemplateId") Integer activityTemplateId);

}
