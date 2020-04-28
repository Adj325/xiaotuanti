package com.adj.xiaotuanti.pojo.vo;

import com.adj.xiaotuanti.pojo.Freedom;

public class UserVo{
    private String id;
    private String realName;
    private String nickName;
    private FreedomVo[][] freedoms;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public FreedomVo[][] getFreedoms() {
        return freedoms;
    }

    public void setFreedoms(FreedomVo[][] freedoms) {
        this.freedoms = freedoms;
    }
}
