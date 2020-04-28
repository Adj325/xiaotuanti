package com.adj.xiaotuanti.pojo;

public class Token {
    private String openId;
    private String userAgent = "";
    private String host = "";

    private Token() {

    }

    public Token(String openId, String host, String userAgent) {
        this.openId = openId;
        this.host = host;
        this.userAgent = userAgent;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
