package com.adj.xiaotuanti.pojo;

public class Relation_Part_User {

    private Integer id;
    private Part part;
    private User user;

    public Relation_Part_User() {
    }

    public Relation_Part_User(Part part, User user) {
        this.part = part;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Part getPart() {
        return part;
    }

    public User getUser() {
        return user;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
