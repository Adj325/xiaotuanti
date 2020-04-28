package com.adj.xiaotuanti.pojo;

import java.util.ArrayList;
import java.util.List;

public class Free {
    private String name;
    private Integer begTime;
    private Integer endTime;
    private String begTimeStr;
    private String endTimeStr;
    private Integer last;
    private Integer type;
    private List<String> userIds;
    private String proportion;

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBegTimeStr() {
        return begTimeStr;
    }

    public void setBegTimeStr(String begTimeStr) {
        this.begTimeStr = begTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
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

    public Integer getLast() {
        return last;
    }

    public void setLast(Integer last) {
        this.last = last;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
