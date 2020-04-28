
package com.adj.xiaotuanti.pojo;

import java.util.Map;

public class Bin {
    private String id;
    private String path;
    private String type;
    private String name;
    public Bin(){}

    public Bin(Map<String, Object> map){

    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }
}
