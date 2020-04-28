package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Comment;
import com.adj.xiaotuanti.pojo.Relation_Activity_Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("Relation_Activity_CommentDAO")
public interface Relation_Activity_CommentDAO {

    void addRelation(@Param("relation") Relation_Activity_Comment Relation);

    void updateRelation(@Param("relation") Relation_Activity_Comment Relation);

    void deleteRelation(@Param("relation") Relation_Activity_Comment Relation);

    void deleteRelationById(@Param("relationId") String RelationId);

    Relation_Activity_Comment getRelation(@Param("relation") Relation_Activity_Comment Relation);

    Relation_Activity_Comment getRelationById(@Param("relationId") String RelationId);

    List<Relation_Activity_Comment> getAllRelations();

    Relation_Activity_Comment getRelationByActivity(@Param("activity") Activity activity);

    List<Relation_Activity_Comment> getRelationsByActivity(@Param("activity") Activity activity);

    void deleteRelationByActivity(@Param("activity") Activity activity);

    void deleteRelationsByActivity(@Param("activity") Activity activity);

    Relation_Activity_Comment getRelationByComment(@Param("comment") Comment comment);

    List<Relation_Activity_Comment> getRelationsByComment(@Param("comment") Comment comment);

    void deleteRelationByComment(@Param("comment") Comment comment);

    void deleteRelationsByComment(@Param("comment") Comment comment);

    Relation_Activity_Comment getRelationByActivityIdAndCommentId(@Param("activityId") String activityId, @Param("commentId") String commentId);

    List<Relation_Activity_Comment> getRelationsByActivityIdAndCommentId(@Param("activityId") String activityId, @Param("commentId") String commentId);

    void deleteRelationByActivityIdAndCommentId(@Param("activityId") String activityId, @Param("commentId") String commentId);

    void deleteRelationsByActivityIdAndCommentId(@Param("activityId") String activityId, @Param("commentId") String commentId);

}
