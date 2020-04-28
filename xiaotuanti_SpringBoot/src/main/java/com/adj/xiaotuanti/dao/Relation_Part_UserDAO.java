package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Part;
import com.adj.xiaotuanti.pojo.Relation_Part_User;
import com.adj.xiaotuanti.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("Relation_Part_UserDAO")
public interface Relation_Part_UserDAO {

    Set<String> getUserIdxByPartId(@Param("partId") Integer partId);

    void addRelation(@Param("relation") Relation_Part_User Relation);

    void updateRelation(@Param("relation") Relation_Part_User Relation);

    void deleteRelation(@Param("relation") Relation_Part_User Relation);

    void deleteRelationById(@Param("RelationId") String RelationId);

    Relation_Part_User getRelation(@Param("relation") Relation_Part_User Relation);

    Relation_Part_User getRelationById(@Param("RelationId") String RelationId);

    List<Relation_Part_User> getAllRelations();

    Relation_Part_User getRelationByPart(@Param("part") Part part);

    List<Relation_Part_User> getRelationsByPart(@Param("part") Part part);

    void deleteRelationByPart(@Param("part") Part part);

    void deleteRelationsByPart(@Param("part") Part part);

    Relation_Part_User getRelationByUser(@Param("user") User user);

    List<Relation_Part_User> getRelationsByUser(@Param("user") User user);

    void deleteRelationByUser(@Param("user") User user);

    void deleteRelationsByUser(@Param("user") User user);

    Relation_Part_User getRelationByPartIdAndUserId(@Param("partId") String partId, @Param("userId") String userId);

    List<Relation_Part_User> getRelationsByPartIdAndUserId(@Param("partId") String partId, @Param("userId") String userId);

    void deleteRelationByPartIdAndUserId(@Param("partId") String partId, @Param("userId") String userId);

    void deleteRelationsByPartIdAndUserId(@Param("partId") String partId, @Param("userId") String userId);

}
