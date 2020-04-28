package com.adj.xiaotuanti.pojo;

import com.adj.xiaotuanti.util.FreeTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Freedom {

    private Integer id;
    @JsonIgnore
    private User userOwner;
    private Integer day;
    private Integer begTime;
    private Integer endTime;

    public Freedom() {
    }

    public Freedom(User userOwner, Integer day, Integer begTime, Integer endTime) {
        this.userOwner = userOwner;
        this.day = day;
        this.begTime = begTime;
        this.endTime = endTime;
    }

    public Freedom(Integer day, Integer begTime, Integer endTime) {
        this.day = day;
        this.begTime = begTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
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

    public void setBegTime(String begTime) {
        this.begTime = FreeTimeUtils.h2m(begTime);
    }

    public void setEndTime(String endTime) {
        this.endTime = FreeTimeUtils.h2m(endTime);
    }
}
