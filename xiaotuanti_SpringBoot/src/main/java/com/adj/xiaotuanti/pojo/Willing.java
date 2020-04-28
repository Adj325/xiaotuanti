package com.adj.xiaotuanti.pojo;

import java.util.Map;

public class Willing {
    private String id;
    private User userOwner;
    private Activity activity;
    private Integer type;

    public Willing(){}

    public Willing(User user, Activity activity){
        this.userOwner = user;
        this.activity = activity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userowner) {
        this.userOwner = userowner;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}