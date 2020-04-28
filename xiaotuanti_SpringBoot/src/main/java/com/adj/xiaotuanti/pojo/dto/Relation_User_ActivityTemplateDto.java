package com.adj.xiaotuanti.pojo.dto;

import com.adj.xiaotuanti.pojo.Relation_User_ActivityTemplate;
import com.adj.xiaotuanti.pojo.User;

import java.util.Map;

public class Relation_User_ActivityTemplateDto extends Relation_User_ActivityTemplate {

    private Integer userId;
    private Integer activityTemplateId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActivityTemplateId() {
        return activityTemplateId;
    }

    public void setActivityTemplateId(Integer activityTemplateId) {
        this.activityTemplateId = activityTemplateId;
    }
}
