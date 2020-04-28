package com.adj.xiaotuanti.pojo;

import com.adj.xiaotuanti.pojo.constants.MemberLevel;
import com.adj.xiaotuanti.pojo.vo.FreedomVo;
import org.nlpcn.commons.lang.util.StringUtil;

public class Participate {

    private String section;
    private String job = "";
    private MemberLevel level = null;

    // 联系方式
    private String stuno = "";
    private String qq = "";
    private String weixin = "";
    private String email = "";
    private String phone = "";

    // 住址信息
    private Integer gender;
    private Integer grade;
    private String dormitory = "";

    private Boolean willing;
    private String name;
    private String userId;
    private String memberId;
    private FreedomVo[][] freedoms;
    private boolean[] partOfficialStatus;
    private boolean[] partCandidateStatus;
    private int[][] partFreeDetails;
    private Integer officialPart;
    private Integer recordNumber;

    private Double priority;


    public Participate(User user, Member member) {
        name = user.getNickName();
        if (StringUtil.isNotBlank(user.getRealName())) {
            name = user.getRealName();
        }
        userId = user.getId();
        if (member != null) {
            memberId = member.getId();
            job = member.getJob();
            if (member.getSection() != null) {
                section = member.getSection().getName();
            } else {
                section = "无部门";
            }
            recordNumber = member.getRecordNumber();
        } else {
            memberId = null;
            job = "志愿者";
            section = "无部门";
            recordNumber =0;
        }
        grade = user.getGrade();
        gender = user.getGender();
        dormitory = user.getDormitory();
    }

    public int getPartOfficialNumber() {
        int num = 0;
        for (boolean status : this.partOfficialStatus) {
            if (status) {
                num++;
            }
        }
        return num;
    }

    public Double getPriority() {
        return priority;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
    }

    public void setPartFreeDetails(int partIdx, int freeBegDetails, int freeEndDetail) {
        int[] freeDetails = {freeBegDetails, freeEndDetail};
        partFreeDetails[partIdx] = freeDetails;
    }

    public int[] getPartFreeDetails(int partIdx) {
        return partFreeDetails[partIdx];
    }

    public Participate() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public FreedomVo[][] getFreedoms() {
        return freedoms;
    }

    public void setFreedomVos(FreedomVo[][] freedoms) {
        this.freedoms = freedoms;
    }

    public void setFreedoms(FreedomVo[][] freedoms) {
        this.freedoms = freedoms;
    }

    public boolean[] getPartOfficialStatus() {
        return partOfficialStatus;
    }

    public void setPartOfficialStatus(int partIdx, boolean status) {
        this.partOfficialStatus[partIdx] = status;
    }

    public void setPartOfficialStatus(boolean[] partOfficialStatus) {
        this.partOfficialStatus = partOfficialStatus;
    }

    public boolean[] getPartCandidateStatus() {
        return partCandidateStatus;
    }

    public void setPartCandidateStatus(boolean[] partCandidateStatus) {
        this.partCandidateStatus = partCandidateStatus;
    }

    public Integer getOfficialPart() {
        return officialPart;
    }

    public void setOfficialPart(Integer officialPart) {
        this.officialPart = officialPart;
    }

    public Integer getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(Integer recordNumber) {
        this.recordNumber = recordNumber;
    }

    public int[][] getPartFreeDetails() {
        return partFreeDetails;
    }

    public void setPartFreeDetails(int[][] partFreeDetails) {
        this.partFreeDetails = partFreeDetails;
    }

    public void setPartCandidateStatus(int partId, boolean b) {
        this.partCandidateStatus[partId] = b;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public MemberLevel getLevel() {
        return level;
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

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public Boolean getWilling() {
        return willing;
    }

    public void setWilling(Boolean willing) {
        this.willing = willing;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return String.format("uid:%s, priority:%s", userId, priority);
    }
}
