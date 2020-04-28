package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Part;
import com.adj.xiaotuanti.pojo.Relation_Part_User;
import com.adj.xiaotuanti.pojo.User;

import java.util.List;

public interface Relation_Part_UserService {

    void addRelation(Relation_Part_User relation);

    void updateRelation(Relation_Part_User relation);

    void deleteRelation(Relation_Part_User relation);

    void deleteRelationById(String RelationId);

    Relation_Part_User getRelation(Relation_Part_User relation);

    Relation_Part_User getRelationById(String RelationId);

    List<Relation_Part_User> getAllRelations();

    Relation_Part_User getRelationByPart(Part part);

    List<Relation_Part_User> getRelationsByPart(Part part);

    void deleteRelationByPart(Part part);

    void deleteRelationsByPart(Part part);

    Relation_Part_User getRelationByUser(User user);

    List<Relation_Part_User> getRelationsByUser(User user);

    void deleteRelationByUser(User user);

    void deleteRelationsByUser(User user);

}