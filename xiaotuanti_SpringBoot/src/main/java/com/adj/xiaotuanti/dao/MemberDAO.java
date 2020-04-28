package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MemberDAO")
public interface MemberDAO {

    Integer getMembersNumBySectionId(@Param("sectionId") String sectionId);

    Member getMemberById(@Param("memberId") String memberId);

    Member getMemberByUserAndTeam(@Param("member") Member member);

    Member getMemberByUserIdAndTeamId(@Param("userId") String userId, @Param("teamId") String teamId);

    Member getMemberByUserIdAndActivityId(@Param("userId") String userId, @Param("activityId") String activityId);

    List<Member> getAllMembers();

    List<Member> getMembersByTeamId(@Param("teamId") String teamId);

    List<Member> getMembersBySectionId(@Param("sectionId") String sectionId);

    void addMember(@Param("member") Member member);

    void deleteMember(@Param("member") Member member);

    void deleteMemberById(@Param("memberId") String memberId);

    void updateMember(@Param("member") Member member);

    void revokeManagerPermissionByTeamId(@Param("teamId") String teamId);

}
