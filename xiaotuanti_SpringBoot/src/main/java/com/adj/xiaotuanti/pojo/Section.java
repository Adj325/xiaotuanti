package com.adj.xiaotuanti.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Section {
    private String id;
    private String name;
    private Integer memberNum;

    @JsonIgnore
    private Team team;

    public Integer getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
