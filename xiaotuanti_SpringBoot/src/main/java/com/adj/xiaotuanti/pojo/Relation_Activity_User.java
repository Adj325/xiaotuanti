package com.adj.xiaotuanti.pojo;

public class Relation_Activity_User {

    private Integer id;
    private Activity activity;
    private User user;

    public Relation_Activity_User() {
    }

    public Relation_Activity_User(Activity activity, User user) {
        this.activity = activity;
        this.user = user;
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

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
