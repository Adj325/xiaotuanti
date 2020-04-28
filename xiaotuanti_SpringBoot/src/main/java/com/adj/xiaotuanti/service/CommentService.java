
package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Comment;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    void updateComment(Comment comment);

    void deleteComment(Comment comment);

    void deleteCommentByActivity(Activity activity);

    void deleteCommentByActivityId(String activityId);

    Comment getComment(Comment comment);

    Comment getCommentById(String commentId);

    List<Comment> getCommentsByActivityId(String activityId);

    List<Comment> getCommentsByUserId(String userId);

    Boolean isCommented(String activityId, String userId);

    List<Comment> getCommentsByPostId(Integer postId);
}
    