package com.adj.xiaotuanti.pojo;

import java.util.Map;

public class Relation_Tag_Activity {

    private Integer id;
    private Tag tag;
    private Activity activity;

    public Relation_Tag_Activity() {
    }

    public Relation_Tag_Activity(Tag tag, Activity activity) {
        this.tag = tag;
        this.activity = activity;
    }

    public Relation_Tag_Activity(Map<String, Object> map) {
        this.id = (Integer) map.get("id");
        this.tag = (Tag) map.get("tag");
        this.activity = (Activity) map.get("activity");
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

    public Activity getActivity() {
        return activity;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
