package com.adj.xiaotuanti.pojo;

import java.util.Map;

public class Relation_Activity_Comment {

    private Integer id;
    private Activity activity;
    private Comment comment;

    public Relation_Activity_Comment() {
    }

    public Relation_Activity_Comment(Map<String, Object> map) {
        this.id = (Integer) map.get("id");
        this.activity = (Activity) map.get("activity");
        this.comment = (Comment) map.get("comment");
    }

    public Relation_Activity_Comment(Activity activity, Comment comment) {
        this.activity = activity;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public Comment getComment() {
        return comment;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
