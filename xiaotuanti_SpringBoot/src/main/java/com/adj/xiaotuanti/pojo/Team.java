package com.adj.xiaotuanti.pojo;

import java.util.Map;

public class Team {
    private String id;
    private String name;
    private String introduction;
    private String invite;

    private User userOwner;
    private Member memberOwner;

    public Team(){}

    public Team(Map<String, Object> map){
        this.id = (String) map.get("teamId");
        this.name = (String) map.get("name");
        this.invite = (String) map.get("invite");
        this.introduction = (String) map.get("introduction");
    }

    public String getInvite() {
        return invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public Member getMemberOwner() {
        return memberOwner;
    }

    public void setMemberOwner(Member memberOwner) {
        this.memberOwner = memberOwner;
    }
}
