package com.adj.xiaotuanti.pojo;

import java.util.Date;
import java.util.Map;

public class Comment {
    private String id;
    private String content;
    private Date postTime;

    private User userAuthor;

    public Comment() {
    }

    public Comment(Map<String, Object> map) {
        id = (String) map.get("commentId");
    }

    public User getUserAuthor() {
        return userAuthor;
    }

    public void setUserAuthor(User userAuthor) {
        this.userAuthor = userAuthor;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setId(String id) {
        this.id = id;
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
}
