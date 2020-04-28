package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.Relation_Tag_ActivityDAO;
import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Relation_Tag_Activity;
import com.adj.xiaotuanti.pojo.Tag;
import com.adj.xiaotuanti.service.Relation_Tag_ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("Relation_Tag_ActivityService")
public class Relation_Tag_ActivityServiceImpl implements Relation_Tag_ActivityService {

    @Autowired
    private Relation_Tag_ActivityDAO relationDAO;

    public void addRelation(Relation_Tag_Activity relation) {
        relationDAO.addRelation(relation);
    }

    public void updateRelation(Relation_Tag_Activity relation) {
        relationDAO.updateRelation(relation);
    }

    public void deleteRelation(Relation_Tag_Activity relation) {
        relationDAO.deleteRelation(relation);
    }

    public void deleteRelationById(String RelationId) {
        relationDAO.deleteRelationById(RelationId);
    }

    public Relation_Tag_Activity getRelation(Relation_Tag_Activity relation) {
        return relationDAO.getRelation(relation);
    }

    public Relation_Tag_Activity getRelationById(String RelationId) {
        return relationDAO.getRelationById(RelationId);
    }

    public List<Relation_Tag_Activity> getAllRelations() {
        return relationDAO.getAllRelations();
    }

    public Relation_Tag_Activity getRelationByTag(Tag tag) {
        return relationDAO.getRelationByTag(tag);
    }

    public List<Relation_Tag_Activity> getRelationsByTag(Tag tag) {
        return relationDAO.getRelationsByTag(tag);
    }

    public void deleteRelationByTag(Tag tag) {
        relationDAO.getRelationByTag(tag);
    }

    public void deleteRelationsByTag(Tag tag) {
        relationDAO.getRelationsByTag(tag);
    }

    public Relation_Tag_Activity getRelationByActivity(Activity activity) {
        return relationDAO.getRelationByActivity(activity);
    }

    public List<Relation_Tag_Activity> getRelationsByActivity(Activity activity) {
        return relationDAO.getRelationsByActivity(activity);
    }

    public void deleteRelationByActivity(Activity activity) {
        relationDAO.deleteRelationByActivity(activity);
    }

    public void deleteRelationsByActivity(Activity activity) {
        relationDAO.deleteRelationsByActivity(activity);
    }

}