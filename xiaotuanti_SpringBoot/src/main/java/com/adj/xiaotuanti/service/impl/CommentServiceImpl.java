
package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.CommentDAO;
import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Comment;
import com.adj.xiaotuanti.service.CommentService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    private final CommentDAO commentDAO;

    @Autowired
    public CommentServiceImpl(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    public void addComment(Comment comment) {
        commentDAO.addComment(comment);
    }

    public void updateComment(Comment comment) {
        commentDAO.updateComment(comment);
    }

    public void deleteComment(Comment comment) {
        commentDAO.deleteComment(comment);
    }

    public void deleteCommentByActivity(Activity activity) {
        commentDAO.deleteCommentByActivity(activity);
    }

    public void deleteCommentByActivityId(String activityId) {
        commentDAO.deleteCommentByActivityId(activityId);
    }

    public Comment getComment(Comment comment) {
        return commentDAO.getComment(comment);
    }

    public Comment getCommentById(String commentId) {
        return commentDAO.getCommentById(commentId);
    }

    public List<Comment> getCommentsByActivityId(String activityId) {
        return commentDAO.getCommentsByActivityId(activityId);
    }

    public List<Comment> getCommentsByUserId(String userId) {
        return commentDAO.getCommentsByUserId(userId);
    }

    public Boolean isCommented(String activityId, String userId) {
        return commentDAO.isCommented(activityId, userId) != 0;
    }

    @Override
    public List<Comment> getCommentsByPostId(Integer postId) {
        return commentDAO.getCommentsByPostId(postId);
    }
}
