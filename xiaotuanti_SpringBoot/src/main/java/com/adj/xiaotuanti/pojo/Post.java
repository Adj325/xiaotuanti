package com.adj.xiaotuanti.pojo;

import java.util.Date;
import java.util.Map;

public class Post {

    private Integer id;
    private User userAuthor;
    private String title;
    private String content;
    private Date postTime;
    private Integer type;

    public Post() {
    }

    public Post(Map<String, Object> map) {
        this.title = (String) map.get("title");
        this.content = (String) map.get("content");
        this.postTime = new Date();
        this.type = (Integer) map.get("type");
    }

    public Integer getId() {
        return id;
    }

    public User getUserAuthor() {
        return userAuthor;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserAuthor(User userAuthor) {
        this.userAuthor = userAuthor;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
