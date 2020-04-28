package com.adj.xiaotuanti.pojo;

import com.adj.xiaotuanti.util.FreeTimeUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.nlpcn.commons.lang.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part {
    private String id;
    private Integer order;
    private Integer begTime;
    private Integer endTime;
    private String content;

    @JsonIgnore
    private Activity activity;
    private String begTimeStr;
    private String endTimeStr;

    // 活动安排相关
    @JsonIgnore
    private Double priority;
    private Integer targetNumber;
    private Integer candidateNumber;
    private Integer officialNumber;
    private Set<String> candidateIds;
    private Set<String> officialIds;

    public Part() {
    }

    public String toString() {
        return String.format("uid:%s, priority:%s", id, priority);
//        return String.format("id:%s, order:%s, activity:%s\nbegTime:%s, endTime:%s",
//                id, order, activity.getId(), begTimeStr, endTimeStr);
    }

    public Part(Map map) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
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

    public Double getPriority() {
        return priority;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
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

    public Integer getCandidateNumber() {
        return candidateNumber;
    }

    public void setCandidateNumber(Integer candidateNumber) {
        this.candidateNumber = candidateNumber;
    }

    public Integer getOfficialNumber() {
        return officialNumber;
    }

    public void setOfficialNumber(Integer officialNumber) {
        this.officialNumber = officialNumber;
    }

    public Set<String> getCandidateIds() {
        return candidateIds;
    }

    public void setCandidateIds(Set<String> candidateIds) {
        this.candidateIds = candidateIds;
    }

    public Set<String> getOfficialIds() {
        return officialIds;
    }

    public void setOfficialIds(Set<String> officialIds) {
        this.officialIds = officialIds;
    }
}
