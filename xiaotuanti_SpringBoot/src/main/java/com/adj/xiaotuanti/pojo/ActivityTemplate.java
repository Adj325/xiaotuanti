package com.adj.xiaotuanti.pojo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.adj.xiaotuanti.pojo.Team;
import com.adj.xiaotuanti.pojo.dto.ActivityTemplateDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ActivityTemplate {

    private Integer id;
    private String name;
    private Boolean open;
    private Date createTime;
    private String introduction;
    private List<Tag> tags;
    private Team teamOwner;
    private Activity sourceActivity;
    private List<PartTemplate> parts;
    private Integer collectNumber;

    public ActivityTemplate() {
    }

    public ActivityTemplate(Activity activity) {
        this.name = activity.getName();
        this.introduction = activity.getIntroduction();
        this.open = activity.getOpen();
        this.teamOwner = activity.getTeam();
        this.tags = activity.getTags();
        this.sourceActivity = activity;
    }

    public ActivityTemplate(ActivityTemplateDto activityTemplateDto) {
        this.name = activityTemplateDto.getName();
        this.introduction = activityTemplateDto.getIntroduction();
        this.open = activityTemplateDto.getOpen();
        this.teamOwner = activityTemplateDto.getTeamOwner();
        this.tags = activityTemplateDto.getTags();
    }

    public Integer getId() {
        return id;
    }

    public Team getTeamOwner() {
        return teamOwner;
    }

    public String getName() {
        return name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTeamOwner(Team teamOwner) {
        this.teamOwner = teamOwner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getOpen() {
        return open;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<PartTemplate> getParts() {
        return parts;
    }

    public void setParts(List<PartTemplate> parts) {
        this.parts = parts;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Activity getSourceActivity() {
        return sourceActivity;
    }

    public void setSourceActivity(Activity sourceActivity) {
        this.sourceActivity = sourceActivity;
    }

    public Integer getCollectNumber() {
        return collectNumber;
    }

    public void setCollectNumber(Integer collectNumber) {
        this.collectNumber = collectNumber;
    }
}
