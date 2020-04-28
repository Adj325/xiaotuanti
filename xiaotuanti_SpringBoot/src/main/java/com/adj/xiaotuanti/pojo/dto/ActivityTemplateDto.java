package com.adj.xiaotuanti.pojo.dto;

import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.PartTemplate;

import java.util.List;

public class ActivityTemplateDto extends ActivityTemplate {
    private String activityId;
    private String tagString;
    private List<PartTemplate> parts;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }
}
