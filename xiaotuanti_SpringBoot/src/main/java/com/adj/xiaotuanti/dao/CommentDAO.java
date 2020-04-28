
package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Comment;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CommentDAO")
public interface CommentDAO {

    void addComment(@Param("comment") Comment comment);

    void updateComment(@Param("comment") Comment comment);

    void deleteComment(@Param("comment") Comment comment);

    void deleteCommentByActivity(@Param("activity") Activity activity);

    void deleteCommentByActivityId(@Param("activityId") String activityId);

    Comment getComment(@Param("comment") Comment comment);

    Comment getCommentById(@Param("commentId") String commentId);

    List<Comment> getCommentsByActivityId(@Param("activityId") String activityId);

    List<Comment> getCommentsByUserId(@Param("userId") String userId);

    Integer isCommented(@Param("activityId") String activityId, @Param("userId") String userId);

    List<Comment> getCommentsByPostId(Integer postId);
}