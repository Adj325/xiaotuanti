package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.Relation_Part_UserDAO;
import com.adj.xiaotuanti.pojo.Part;
import com.adj.xiaotuanti.pojo.Relation_Part_User;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.service.Relation_Part_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("Relation_Part_UserService")
public class Relation_Part_UserServiceImpl implements Relation_Part_UserService {

    @Autowired
    private Relation_Part_UserDAO relationDAO;

    public void addRelation(Relation_Part_User relation) {
        relationDAO.addRelation(relation);
    }

    public void updateRelation(Relation_Part_User relation) {
        relationDAO.updateRelation(relation);
    }

    public void deleteRelation(Relation_Part_User relation) {
        relationDAO.deleteRelation(relation);
    }

    public void deleteRelationById(String RelationId) {
        relationDAO.deleteRelationById(RelationId);
    }

    public Relation_Part_User getRelation(Relation_Part_User relation) {
        return relationDAO.getRelation(relation);
    }

    public Relation_Part_User getRelationById(String RelationId) {
        return relationDAO.getRelationById(RelationId);
    }

    public List<Relation_Part_User> getAllRelations() {
        return relationDAO.getAllRelations();
    }

    public Relation_Part_User getRelationByPart(Part part) {
        return relationDAO.getRelationByPart(part);
    }

    public List<Relation_Part_User> getRelationsByPart(Part part) {
        return relationDAO.getRelationsByPart(part);
    }

    public void deleteRelationByPart(Part part) {
        relationDAO.deleteRelationByPart(part);
    }

    public void deleteRelationsByPart(Part part) {
        relationDAO.deleteRelationsByPart(part);
    }

    public Relation_Part_User getRelationByUser(User user) {
        return relationDAO.getRelationByUser(user);
    }

    public List<Relation_Part_User> getRelationsByUser(User user) {
        return relationDAO.getRelationsByUser(user);
    }

    public void deleteRelationByUser(User user) {
        relationDAO.getRelationByUser(user);
    }

    public void deleteRelationsByUser(User user) {
        relationDAO.getRelationsByUser(user);
    }

}