package com.adj.xiaotuanti.pojo;

import com.adj.xiaotuanti.pojo.constants.ActivityStatus;
import com.adj.xiaotuanti.pojo.dto.ActivityDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Activity {
    private String id;
    private Team team;
    private String name;
    private String introduction;
    private Date begTime;
    private Date endTime;
    private Date createTime;
    private ActivityStatus status;
    private Boolean open;

    private ActivityTemplate sourceActivityTemplate;
    private User userConductor;
    private Member memberConductor;
    private List<Part> parts;
    private List<Tag> tags;

    // 志愿人数
    private Integer willingNumber;
    private Integer officialNumber;

    public Activity(ActivityDto activityDto) {
        this.begTime = activityDto.getDate();
        this.endTime = activityDto.getDate();
        this.parts = activityDto.getParts();
        this.introduction = activityDto.getIntroduction();
        this.name = activityDto.getName();
        this.open = activityDto.getOpen();
        this.createTime = new Date();
        this.tags = new LinkedList<>();
        for(String tagName:activityDto.getTagString().split("#")) {
            this.tags.add(new Tag(tagName));
        }
    }

    // 格式化时间所用
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Activity() {
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getBegTime() {
        try {
            return simpleDateFormat.format(begTime);
        } catch (Exception e) {
            return "";
        }
    }

    public Date getBegTimeDate() {
        return begTime;
    }

    public void setBegTime(Date begTime) {
        this.begTime = begTime;
    }

    public String getEndTime() {
        try {
            return simpleDateFormat.format(endTime);
        } catch (Exception e) {
            return "";
        }
    }

    public Date getEndTimeDate() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getWillingNumber() {
        return willingNumber;
    }

    public void setWillingNumber(Integer willingNumber) {
        this.willingNumber = willingNumber;
    }

    public Integer getOfficialNumber() {
        return officialNumber;
    }

    public void setOfficialNumber(Integer officialNumber) {
        this.officialNumber = officialNumber;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUserConductor() {
        return userConductor;
    }

    public void setUserConductor(User userConductor) {
        this.userConductor = userConductor;
    }

    public Member getMemberConductor() {
        return memberConductor;
    }

    public void setMemberConductor(Member memberConductor) {
        this.memberConductor = memberConductor;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public ActivityTemplate getSourceActivityTemplate() {
        return sourceActivityTemplate;
    }

    public void setSourceActivityTemplate(ActivityTemplate sourceActivityTemplate) {
        this.sourceActivityTemplate = sourceActivityTemplate;
    }
}
