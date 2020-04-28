package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Comment;
import com.adj.xiaotuanti.pojo.Post;
import com.adj.xiaotuanti.pojo.Relation_Post_Comment;

import java.util.List;

public interface Relation_Post_CommentService {

    void addRelation(Relation_Post_Comment relation);

    void updateRelation(Relation_Post_Comment relation);

    void deleteRelation(Relation_Post_Comment relation);

    void deleteRelationById(String RelationId);

    Relation_Post_Comment getRelation(Relation_Post_Comment relation);

    Relation_Post_Comment getRelationById(String RelationId);

    List<Relation_Post_Comment> getAllRelations();

    Relation_Post_Comment getRelationByPost(Post post);

    List<Relation_Post_Comment> getRelationsByPost(Post post);

    void deleteRelationByPost(Post post);

    void deleteRelationsByPost(Post post);

    Relation_Post_Comment getRelationByComment(Comment comment);

    List<Relation_Post_Comment> getRelationsByComment(Comment comment);

    void deleteRelationByComment(Comment comment);

    void deleteRelationsByComment(Comment comment);

}