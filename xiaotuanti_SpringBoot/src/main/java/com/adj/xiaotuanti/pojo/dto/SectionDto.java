package com.adj.xiaotuanti.pojo.dto;

import com.adj.xiaotuanti.pojo.Section;

import java.util.List;

public class SectionDto {
    private String teamId;
    private List<Section> sections;
    private List<String> idsToRemove;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<String> getIdsToRemove() {
        return idsToRemove;
    }

    public void setIdsToRemove(List<String> idsToRemove) {
        this.idsToRemove = idsToRemove;
    }
}
