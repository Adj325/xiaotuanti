package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Comment;
import com.adj.xiaotuanti.pojo.Post;
import com.adj.xiaotuanti.pojo.Relation_Post_Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("Relation_Post_CommentDAO")
public interface Relation_Post_CommentDAO {

    void addRelation(@Param("relation") Relation_Post_Comment Relation);

    void updateRelation(@Param("relation") Relation_Post_Comment Relation);

    void deleteRelation(@Param("relation") Relation_Post_Comment Relation);

    void deleteRelationById(@Param("relationId") String RelationId);

    Relation_Post_Comment getRelation(@Param("relation") Relation_Post_Comment Relation);

    Relation_Post_Comment getRelationById(@Param("relationId") String RelationId);

    List<Relation_Post_Comment> getAllRelations();

    Relation_Post_Comment getRelationByPost(@Param("post") Post post);

    List<Relation_Post_Comment> getRelationsByPost(@Param("post") Post post);

    void deleteRelationByPost(@Param("post") Post post);

    void deleteRelationsByPost(@Param("post") Post post);

    Relation_Post_Comment getRelationByComment(@Param("comment") Comment comment);

    List<Relation_Post_Comment> getRelationsByComment(@Param("comment") Comment comment);

    void deleteRelationByComment(@Param("comment") Comment comment);

    void deleteRelationsByComment(@Param("comment") Comment comment);

    Relation_Post_Comment getRelationByPostIdAndCommentId(@Param("postId") String postId, @Param("commentId") String commentId);

    List<Relation_Post_Comment> getRelationsByPostIdAndCommentId(@Param("postId") String postId, @Param("commentId") String commentId);

    void deleteRelationByPostIdAndCommentId(@Param("postId") String postId, @Param("commentId") String commentId);

    void deleteRelationsByPostIdAndCommentId(@Param("postId") String postId, @Param("commentId") String commentId);

}
