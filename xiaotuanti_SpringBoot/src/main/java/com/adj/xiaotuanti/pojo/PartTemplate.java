package com.adj.xiaotuanti.pojo;

import com.adj.xiaotuanti.util.FreeTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.nlpcn.commons.lang.util.StringUtil;

public class PartTemplate {

    private Integer id;
    private Integer order;
    private Integer begTime;
    private Integer endTime;
    private Integer targetNumber;
    private String content;
    private String begTimeStr;
    private String endTimeStr;

    @JsonIgnore
    private ActivityTemplate activityTemplate;

    public PartTemplate() {
    }

    public PartTemplate(Part part) {
        this.order = part.getOrder();
        this.begTime = part.getBegTime();
        this.endTime = part.getEndTime();
        this.content = part.getContent();
        this.targetNumber = part.getTargetNumber();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ActivityTemplate getActivityTemplate() {
        return activityTemplate;
    }

    public void setActivityTemplate(ActivityTemplate activityTemplate) {
        this.activityTemplate = activityTemplate;
    }

    public Integer getBegTime() {
        return begTime;
    }

    public void setBegTime(Integer begTime) {
        this.begTime = begTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getTargetNumber() {
        return targetNumber;
    }

    public void setTargetNumber(Integer targetNumber) {
        this.targetNumber = targetNumber;
    }

    public String getBegTimeStr() {
        return FreeTimeUtils.m2h(begTime);
    }

    public void setBegTimeStr(String begTimeStr) {
        if (StringUtil.isNotBlank(begTimeStr)) {
            this.begTime = FreeTimeUtils.h2m(begTimeStr);
        }
        this.begTimeStr = begTimeStr;
    }

    public String getEndTimeStr() {
        return FreeTimeUtils.m2h(endTime);
    }

    public void setEndTimeStr(String endTimeStr) {
        if (StringUtil.isNotBlank(endTimeStr)) {
            this.endTime = FreeTimeUtils.h2m(endTimeStr);
        }
        this.endTimeStr = endTimeStr;
    }

}
