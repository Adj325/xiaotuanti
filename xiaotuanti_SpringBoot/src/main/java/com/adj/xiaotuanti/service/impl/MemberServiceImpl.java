package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.MemberDAO;
import com.adj.xiaotuanti.pojo.Member;
import com.adj.xiaotuanti.pojo.Team;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.service.MemberService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

    private final MemberDAO memberDAO;

    @Autowired
    public MemberServiceImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    public Member getMemberByUserIdAndTeamId(String userId, String teamId) {
        return memberDAO.getMemberByUserIdAndTeamId(userId, teamId);
    }

    public Member getMemberById(String memberId) {
        return memberDAO.getMemberById(memberId);
    }

    public Member getMemberByUserAndTeam(Member member) {
        return memberDAO.getMemberByUserAndTeam(member);
    }

    public Member getMemberByUserAndTeam(User user, Team team) {
        Member member = new Member();
        member.setUser(user);
        member.setTeam(team);
        return memberDAO.getMemberByUserAndTeam(member);
    }

    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }

    // 根据团体找成员
    public List<Member> getMembersByTeamId(String teamId) {
        return memberDAO.getMembersByTeamId(teamId);
    }

    public List<Member> getMembersBySectionId(String sectionId) {
        return memberDAO.getMembersBySectionId(sectionId);
    }

    // 添加成员，team不能为空
    public void addMember(Member member) {

        Member tempMember = getMemberByUserAndTeam(member);
        if (tempMember == null) {
            memberDAO.addMember(member);
        } else {
            updateMember(member);
        }
    }

    public void deleteMember(Member member) {
        memberDAO.deleteMember(member);
    }

    public void deleteMemberById(String memberId) {
        memberDAO.deleteMemberById(memberId);
    }

    // 修改成员，根据id
    public void updateMember(Member member) {
        memberDAO.updateMember(member);
    }

    // 撤销成员的管理员权限
    public void revokeManagerPermissionByTeamId(String teamId) {
        memberDAO.revokeManagerPermissionByTeamId(teamId);
    }
}
