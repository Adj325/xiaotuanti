package com.adj.xiaotuanti.pojo.dto;

import com.adj.xiaotuanti.pojo.Freedom;

import java.util.List;

public class FreedomDto {

    private Integer day;
    private List<Freedom> freedoms;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public List<Freedom> getFreedoms() {
        return freedoms;
    }

    public void setFreedoms(List<Freedom> freedoms) {
        this.freedoms = freedoms;
    }
}
