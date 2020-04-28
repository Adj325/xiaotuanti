package com.adj.xiaotuanti.pojo;

import java.util.Map;

import com.adj.xiaotuanti.pojo.Post;
import com.adj.xiaotuanti.pojo.Comment;

public class Relation_Post_Comment {

    private Integer id;
    private Post post;
    private Comment comment;

    public Relation_Post_Comment() {
    }

    public Relation_Post_Comment(Map<String, Object> map) {
        this.id = (Integer) map.get("id");
        this.post = (Post) map.get("post");
        this.comment = (Comment) map.get("comment");
    }

    public Relation_Post_Comment(Post post, Comment comment) {
        this.post = post;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
