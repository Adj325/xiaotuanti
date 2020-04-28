package com.adj.xiaotuanti.pojo;

import java.util.Map;

public class Relation_User_ActivityTemplate {

    private Integer id;
    private User user;
    private ActivityTemplate activityTemplate;

    public Relation_User_ActivityTemplate() {
    }

    public Relation_User_ActivityTemplate(Map<String, Object> map) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public ActivityTemplate getActivityTemplate() {
        return activityTemplate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setActivityTemplate(ActivityTemplate activityTemplate) {
        this.activityTemplate = activityTemplate;
    }
}
