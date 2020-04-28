package com.adj.xiaotuanti.pojo.dto;

import java.util.List;

public class ManagerDto {
    private String teamId;
    private List<String> managerMemberIds;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public List<String> getManagerMemberIds() {
        return managerMemberIds;
    }

    public void setManagerMemberIds(List<String> managerMemberIds) {
        this.managerMemberIds = managerMemberIds;
    }
}
