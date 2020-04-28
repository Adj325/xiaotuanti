package com.adj.xiaotuanti.pojo.vo;

import com.adj.xiaotuanti.pojo.Freedom;
import com.adj.xiaotuanti.util.FreeTimeUtils;

public class FreedomVo extends Freedom {

    private String begTimeStr;
    private String endTimeStr;

    public FreedomVo() {
    }

    public FreedomVo(Freedom freedom) {
        this.setId(freedom.getId());
        this.setDay(freedom.getDay());
        this.setBegTime(freedom.getBegTime());
        this.setEndTime(freedom.getEndTime());
        this.setDay(freedom.getDay());
        setBegTimeStr(freedom.getBegTime());
        setEndTimeStr(freedom.getEndTime());
    }

    public void setBegTimeStr(Integer begTime) {
        this.begTimeStr = FreeTimeUtils.m2h(begTime);
    }

    public void setEndTimeStr(Integer endTime) {
        this.endTimeStr = FreeTimeUtils.m2h(endTime);
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
}
