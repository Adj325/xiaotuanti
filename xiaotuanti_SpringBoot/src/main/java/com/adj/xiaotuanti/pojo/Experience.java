
package com.adj.xiaotuanti.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.Map;

public class Experience {
    private String id;
    private String content;
    private Date postTime;

    @JsonIgnore
    private User userAuthor;
    @JsonIgnore
    private Member memberAuthor;
    @JsonIgnore
    private Activity activity;
    @JsonIgnore
    private Team team;

    public Experience() {
    }

    public String getId() {
        return id;
    }

    public User getUserAuthor() {
        return userAuthor;
    }

    public Member getMemberAuthor() {
        return memberAuthor;
    }

    public void setMemberAuthor(Member memberAuthor) {
        this.memberAuthor = memberAuthor;
    }

    public Team getTeam() {
        return team;
    }

    public String getContent() {
        return content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserAuthor(User userAuthor) {
        this.userAuthor = userAuthor;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
