package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.Relation_User_ActivityTemplateDAO;
import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.Relation_User_ActivityTemplate;
import com.adj.xiaotuanti.pojo.Section;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.service.Relation_User_ActivityTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("relation_User_ActivityTemplateService")
public class Relation_User_ActivityTemplateServiceImpl implements Relation_User_ActivityTemplateService {

    @Autowired
    private Relation_User_ActivityTemplateDAO relationDAO;

    public void addRelation(Relation_User_ActivityTemplate relation) {
        relationDAO.addRelation(relation);
    }

    public void updateRelation(Relation_User_ActivityTemplate relation) {
        relationDAO.updateRelation(relation);
    }

    public void deleteRelation(Relation_User_ActivityTemplate relation) {
        relationDAO.deleteRelation(relation);
    }

    public void deleteRelationById(String RelationId) {
        relationDAO.deleteRelationById(RelationId);
    }

    public Relation_User_ActivityTemplate getRelation(Relation_User_ActivityTemplate relation) {
        return relationDAO.getRelation(relation);
    }

    public Relation_User_ActivityTemplate getRelationById(String RelationId) {
        return relationDAO.getRelationById(RelationId);
    }

    public List<Relation_User_ActivityTemplate> getAllRelations() {
        return relationDAO.getAllRelations();
    }

    public Relation_User_ActivityTemplate getRelationByUser(User user) {
        return relationDAO.getRelationByUser(user);
    }

    public List<Relation_User_ActivityTemplate> getRelationsByUser(User user) {
        return relationDAO.getRelationsByUser(user);
    }

    public void deleteRelationByUser(User user) {
        relationDAO.getRelationByUser(user);
    }

    public void deleteRelationsByUser(User user) {
        relationDAO.getRelationsByUser(user);
    }

    public Relation_User_ActivityTemplate getRelationByActivityTemplate(ActivityTemplate activityTemplate) {
        return relationDAO.getRelationByActivityTemplate(activityTemplate);
    }

    public List<Relation_User_ActivityTemplate> getRelationsByActivityTemplate(ActivityTemplate activityTemplate) {
        return relationDAO.getRelationsByActivityTemplate(activityTemplate);
    }

    public void deleteRelationByActivityTemplate(ActivityTemplate activityTemplate) {
        relationDAO.getRelationByActivityTemplate(activityTemplate);
    }

    public void deleteRelationsByActivityTemplate(ActivityTemplate activityTemplate) {
        relationDAO.getRelationsByActivityTemplate(activityTemplate);
    }
}