package com.adj.xiaotuanti.pojo.dto;

import com.adj.xiaotuanti.pojo.Willing;

public class WillingDto extends Willing {
    private String activityId;
    private Boolean willing;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Boolean getWilling() {
        return willing;
    }

    public void setWilling(Boolean willing) {
        this.willing = willing;
    }
}
