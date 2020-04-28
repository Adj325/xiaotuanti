package com.adj.xiaotuanti.pojo;

import com.adj.xiaotuanti.pojo.constants.MemberLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

public class Member {
    private String id;
    private String job = "";
    private MemberLevel level = null;

    // 联系方式
    private String stuno = "";
    private String qq = "";
    private String weixin = "";
    private String email = "";
    private String phone = "";

    // 住址信息
    private String city = "";
    private String province = "";
    private String country = "";
    private String address = "";
    private String dormitory = "";

    // 特殊信息
    private String special = "";

    public Member() {
    }

    public Member(Map map) {
        this.id = (String) map.get("memberId");
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
        this.special = (String) map.get("special");
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    @JsonIgnore
    private Team team;

    @JsonIgnore
    private User user;

    private Section section;

    // 活动相关
    private Integer recordNumber;

    public void setRecordNumber(Integer recordNumber) {
        this.recordNumber = recordNumber;
    }

    public Integer getRecordNumber() {
        return recordNumber;
    }

    public MemberLevel getLevel() {
        return level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public void setLevel(MemberLevel level) {
        this.level = level;
    }

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
