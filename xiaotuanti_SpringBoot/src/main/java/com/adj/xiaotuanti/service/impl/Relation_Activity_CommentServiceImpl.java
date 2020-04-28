package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.Relation_Activity_CommentDAO;
import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Comment;
import com.adj.xiaotuanti.pojo.Relation_Activity_Comment;
import com.adj.xiaotuanti.service.Relation_Activity_CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("Relation_Activity_CommentService")
public class Relation_Activity_CommentServiceImpl implements Relation_Activity_CommentService {

    @Autowired
    private Relation_Activity_CommentDAO relationDAO;

    public void addRelation(Relation_Activity_Comment relation) {
        relationDAO.addRelation(relation);
    }

    public void updateRelation(Relation_Activity_Comment relation) {
        relationDAO.updateRelation(relation);
    }

    public void deleteRelation(Relation_Activity_Comment relation) {
        relationDAO.deleteRelation(relation);
    }

    public void deleteRelationById(String RelationId) {
        relationDAO.deleteRelationById(RelationId);
    }

    public Relation_Activity_Comment getRelation(Relation_Activity_Comment relation) {
        return relationDAO.getRelation(relation);
    }

    public Relation_Activity_Comment getRelationById(String RelationId) {
        return relationDAO.getRelationById(RelationId);
    }

    public List<Relation_Activity_Comment> getAllRelations() {
        return relationDAO.getAllRelations();
    }

    public Relation_Activity_Comment getRelationByActivity(Activity activity) {
        return relationDAO.getRelationByActivity(activity);
    }

    public List<Relation_Activity_Comment> getRelationsByActivity(Activity activity) {
        return relationDAO.getRelationsByActivity(activity);
    }

    public void deleteRelationByActivity(Activity activity) {
        relationDAO.getRelationByActivity(activity);
    }

    public void deleteRelationsByActivity(Activity activity) {
        relationDAO.getRelationsByActivity(activity);
    }

    public Relation_Activity_Comment getRelationByComment(Comment comment) {
        return relationDAO.getRelationByComment(comment);
    }

    public List<Relation_Activity_Comment> getRelationsByComment(Comment comment) {
        return relationDAO.getRelationsByComment(comment);
    }

    public void deleteRelationByComment(Comment comment) {
        relationDAO.getRelationByComment(comment);
    }

    public void deleteRelationsByComment(Comment comment) {
        relationDAO.getRelationsByComment(comment);
    }

}