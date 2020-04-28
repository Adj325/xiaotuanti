package com.adj.xiaotuanti.pojo;

import java.util.List;
import java.util.Map;

public class User {
    // 用户标识
    private String id;
    private String openid;
    private String sessionid;
    // 联系方式
    private String stuno;
    private String qq;
    private String weixin;
    private String email;
    private String phone;
    // 住址信息
    private String city;
    private String province;
    private String country;
    private String address;
    private String dormitory;
    // 其它信息
    private String realName;
    private String nickName;
    private Integer gender;
    private Integer grade;
    private String avatarUrl;

    private transient Double priority;
    private transient String password;
    private transient String sessionkey;

    // 活动安排所用-各阶段的空闲时间
    private transient int[][] partFreeTime;
    private transient boolean[] offStatue;
    private transient boolean[] canStatue;
    private transient Integer offPart;
    private transient Member member;

    public String toString() {
        return String.format("id=%s,openid=%s,name=%s,sessionid=%s",
                id, openid, realName, sessionid);
    }

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public User(Map map) {
        // 标识不设置

        // 联系方式
        this.stuno = (String) map.get("stuno");
        this.weixin = (String) map.get("weixin");
        this.qq = (String) map.get("qq");
        this.email = (String) map.get("email");
        this.phone = (String) map.get("phone");
        // 住址信息
        this.city = (String) map.get("city");
        this.province = (String) map.get("province");
        this.country = (String) map.get("country");
        this.address = (String) map.get("address");
        this.dormitory = (String) map.get("dormitory");
        // 其它信息
        this.realName = (String) map.get("realName");
        this.nickName = (String) map.get("nickName");
        this.password = (String) map.get("password");
        this.avatarUrl = (String) map.get("avatarUrl");
        try {
            this.gender = Integer.valueOf((String) map.get("gender"));
            this.grade = Integer.valueOf((String) map.get("grade"));
        }catch (Exception e){
        }

    }

    public Integer getOffPart() {
        if (offPart == null) {
            if (offStatue != null)
                for (Integer partId = 0; partId < offStatue.length; partId++) {
                    if (offStatue[partId]) {
                        offPart = partId;
                        return offPart;
                    }
                }
            return -1;
        }
        return offPart;
    }

    public void setOffPart(Integer offPart) {
        this.offPart = offPart;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public boolean[] getCanStatue() {
        return canStatue;
    }

    public boolean getCanStatue(int partId) {
        return canStatue[partId];
    }

    public void setCanStatue(boolean[] canStatue) {
        this.canStatue = canStatue;
    }

    public void setCanStatue(int partId, boolean statue) {
        this.canStatue[partId] = statue;
    }

    public int[][] getPartFreeTime() {
        return partFreeTime;
    }

    public void setPartFreeTime(int pardId, int i, int j) {
        this.partFreeTime[pardId][0] = i;
        this.partFreeTime[pardId][1] = j;
    }

    public void setPartFreeTime(int[][] partFreeTime) {
        this.partFreeTime = partFreeTime;
    }

    public boolean[] getOffStatue() {
        return offStatue;
    }

    public void setOffStatue(boolean[] partOffStatue) {
        this.offStatue = partOffStatue;
    }

    public void setOffStatue(int partId, boolean partStatue) {
        offPart = partId;
        this.offStatue[partId] = partStatue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getSessionkey() {
        return sessionkey;
    }

    public void setSessionkey(String sessionkey) {
        this.sessionkey = sessionkey;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        if (grade.contains("一"))
            this.grade = 0;
        else if (grade.contains("二"))
            this.grade = 1;
        else if (grade.contains("三"))
            this.grade = 2;
        else if (grade.contains("四"))
            this.grade = 3;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getPriority() {
        return priority;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
    }
}
