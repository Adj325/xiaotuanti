package com.adj.xiaotuanti.pojo;

import java.util.Map;

public class Apply {
    private String id;

    private User userAuthor;
    private Team team;

    public Apply(){}
    public Apply(Map<String, Object> map){
        this.id = (String) map.get("applyId");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUserAuthor() {
        return userAuthor;
    }

    public void setUserAuthor(User userAuthor) {
        this.userAuthor = userAuthor;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
