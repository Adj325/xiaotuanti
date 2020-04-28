package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Member;
import com.adj.xiaotuanti.pojo.Team;
import com.adj.xiaotuanti.pojo.User;

import java.util.List;

public interface MemberService {

    Member getMemberById(String memberId);


    Member getMemberByUserAndTeam(Member member);

    Member getMemberByUserAndTeam(User user, Team team);

    Member getMemberByUserIdAndTeamId(String userId, String teamId);

    List<Member> getAllMembers();

    List<Member> getMembersByTeamId(String teamId);

    List<Member> getMembersBySectionId(String sectionId);

    void addMember(Member member);


    void deleteMember(Member member);

    void deleteMemberById(String memberId);

    void updateMember(Member member);

    // 撤销成员的管理员权限
    void revokeManagerPermissionByTeamId(String teamId);
}
