package com.adj.xiaotuanti.pojo;

import java.util.Map;

public class Item {
    private String id;

    private Team team;

    private String introduction;

    private String contact;

    public Item() {
    }

    public Item(Map map) {
        id = (String) map.get("itemId");
        contact = (String) map.get("contact");
        introduction = (String) map.get("introduction");
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }
}