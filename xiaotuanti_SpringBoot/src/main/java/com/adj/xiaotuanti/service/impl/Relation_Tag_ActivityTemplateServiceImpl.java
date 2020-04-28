package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.Relation_Tag_ActivityTemplateDAO;
import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.Relation_Tag_ActivityTemplate;
import com.adj.xiaotuanti.pojo.Tag;
import com.adj.xiaotuanti.service.Relation_Tag_ActivityTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("Relation_Tag_ActivityTemplateService")
public class Relation_Tag_ActivityTemplateServiceImpl implements Relation_Tag_ActivityTemplateService {

    @Autowired
    private Relation_Tag_ActivityTemplateDAO relationDAO;

    public void addRelation(Relation_Tag_ActivityTemplate relation) {
        relationDAO.addRelation(relation);
    }

    public void updateRelation(Relation_Tag_ActivityTemplate relation) {
        relationDAO.updateRelation(relation);
    }

    public void deleteRelation(Relation_Tag_ActivityTemplate relation) {
        relationDAO.deleteRelation(relation);
    }

    public void deleteRelationById(String RelationId) {
        relationDAO.deleteRelationById(RelationId);
    }

    public Relation_Tag_ActivityTemplate getRelation(Relation_Tag_ActivityTemplate relation) {
        return relationDAO.getRelation(relation);
    }

    public Relation_Tag_ActivityTemplate getRelationById(String RelationId) {
        return relationDAO.getRelationById(RelationId);
    }

    public List<Relation_Tag_ActivityTemplate> getAllRelations() {
        return relationDAO.getAllRelations();
    }

    public Relation_Tag_ActivityTemplate getRelationByTag(Tag tag) {
        return relationDAO.getRelationByTag(tag);
    }

    public List<Relation_Tag_ActivityTemplate> getRelationsByTag(Tag tag) {
        return relationDAO.getRelationsByTag(tag);
    }

    public void deleteRelationByTag(Tag tag) {
        relationDAO.getRelationByTag(tag);
    }

    public void deleteRelationsByTag(Tag tag) {
        relationDAO.getRelationsByTag(tag);
    }

    public Relation_Tag_ActivityTemplate getRelationByActivityTemplate(ActivityTemplate ActivityTemplate) {
        return relationDAO.getRelationByActivityTemplate(ActivityTemplate);
    }

    public List<Relation_Tag_ActivityTemplate> getRelationsByActivityTemplate(ActivityTemplate ActivityTemplate) {
        return relationDAO.getRelationsByActivityTemplate(ActivityTemplate);
    }

    public void deleteRelationByActivityTemplate(ActivityTemplate ActivityTemplate) {
        relationDAO.getRelationByActivityTemplate(ActivityTemplate);
    }

    public void deleteRelationsByActivityTemplate(ActivityTemplate ActivityTemplate) {
        relationDAO.deleteRelationsByActivityTemplate(ActivityTemplate);
    }

}