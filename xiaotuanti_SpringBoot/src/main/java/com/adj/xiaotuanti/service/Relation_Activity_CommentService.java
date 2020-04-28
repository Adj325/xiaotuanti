package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Comment;
import com.adj.xiaotuanti.pojo.Relation_Activity_Comment;

import java.util.List;

public interface Relation_Activity_CommentService {

	void addRelation(Relation_Activity_Comment relation);

	void updateRelation(Relation_Activity_Comment relation);

	void deleteRelation(Relation_Activity_Comment relation);

	void deleteRelationById(String RelationId);

	Relation_Activity_Comment getRelation(Relation_Activity_Comment relation);

	Relation_Activity_Comment getRelationById(String RelationId);

	List<Relation_Activity_Comment> getAllRelations();

	Relation_Activity_Comment getRelationByActivity(Activity activity);

	List<Relation_Activity_Comment> getRelationsByActivity(Activity activity);

	void deleteRelationByActivity(Activity activity);

	void deleteRelationsByActivity(Activity activity);

	Relation_Activity_Comment getRelationByComment(Comment comment);

	List<Relation_Activity_Comment> getRelationsByComment(Comment comment);

	void deleteRelationByComment(Comment comment);

	void deleteRelationsByComment(Comment comment);

}