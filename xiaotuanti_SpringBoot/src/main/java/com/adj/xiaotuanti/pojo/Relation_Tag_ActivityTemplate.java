package com.adj.xiaotuanti.pojo;

public class Relation_Tag_ActivityTemplate {

    private Integer id;
    private Tag tag;
    private ActivityTemplate activityTemplate;

    public Relation_Tag_ActivityTemplate() {
    }

    public Relation_Tag_ActivityTemplate(Tag tag, ActivityTemplate template) {
        this.tag = tag;
        this.activityTemplate = template;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public ActivityTemplate getActivityTemplate() {
        return activityTemplate;
    }

    public void setActivityTemplate(ActivityTemplate activityTemplate) {
        this.activityTemplate = activityTemplate;
    }
}
