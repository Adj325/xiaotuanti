package com.adj.xiaotuanti.pojo.dto;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Part;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class ActivityDto extends Activity {
    private String teamId;
    private Integer activityTemplateId;
    private String tagString;
    private List<Part> parts;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getActivityTemplateId() {
        return activityTemplateId;
    }

    public void setActivityTemplateId(Integer activityTemplateId) {
        this.activityTemplateId = activityTemplateId;
    }

    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    @Override
    public List<Part> getParts() {
        return parts;
    }

    @Override
    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
