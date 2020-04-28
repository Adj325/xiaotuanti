package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.Relation_Post_CommentDAO;
import com.adj.xiaotuanti.pojo.Comment;
import com.adj.xiaotuanti.pojo.Post;
import com.adj.xiaotuanti.pojo.Relation_Post_Comment;
import com.adj.xiaotuanti.service.Relation_Post_CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("Relation_Post_CommentService")
public class Relation_Post_CommentServiceImpl implements Relation_Post_CommentService {

    @Autowired
    private Relation_Post_CommentDAO relationDAO;

    public void addRelation(Relation_Post_Comment relation) {
        relationDAO.addRelation(relation);
    }

    public void updateRelation(Relation_Post_Comment relation) {
        relationDAO.updateRelation(relation);
    }

    public void deleteRelation(Relation_Post_Comment relation) {
        relationDAO.deleteRelation(relation);
    }

    public void deleteRelationById(String relationId) {
        relationDAO.deleteRelationById(relationId);
    }

    public Relation_Post_Comment getRelation(Relation_Post_Comment relation) {
        return relationDAO.getRelation(relation);
    }

    public Relation_Post_Comment getRelationById(String relationId) {
        return relationDAO.getRelationById(relationId);
    }

    public List<Relation_Post_Comment> getAllRelations() {
        return relationDAO.getAllRelations();
    }

    public Relation_Post_Comment getRelationByPost(Post post) {
        return relationDAO.getRelationByPost(post);
    }

    public List<Relation_Post_Comment> getRelationsByPost(Post post) {
        return relationDAO.getRelationsByPost(post);
    }

    public void deleteRelationByPost(Post post) {
        relationDAO.getRelationByPost(post);
    }

    public void deleteRelationsByPost(Post post) {
        relationDAO.getRelationsByPost(post);
    }

    public Relation_Post_Comment getRelationByComment(Comment comment) {
        return relationDAO.getRelationByComment(comment);
    }

    public List<Relation_Post_Comment> getRelationsByComment(Comment comment) {
        return relationDAO.getRelationsByComment(comment);
    }

    public void deleteRelationByComment(Comment comment) {
        relationDAO.getRelationByComment(comment);
    }

    public void deleteRelationsByComment(Comment comment) {
        relationDAO.getRelationsByComment(comment);
    }

}