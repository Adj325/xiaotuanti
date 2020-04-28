package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Team;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TeamDAO")
public interface TeamDAO {

    // 添加团体
    void addTeam(@Param("team") Team team);

    // 更新团体
    void updateTeam(@Param("team") Team team);

    // 销毁团体
    void deleteTeamById(@Param("teamId") String teamId);

    // 根据id获取团体
    Team getTeamById(@Param("teamId") String teamId);

    // 获取所有团体
    List<Team> getAllTeams();

    // 获取用户创建的所有团体
    List<Team> getTeamsUserCreated(@Param("userId") String userId);

    // 获取用户的加入的所有团体
    List<Team> getTeamsUserJoined(@Param("userId") String userId);

    // 根据邀请码获取多个团体
    List<Team> getTeamsByInvite(@Param("invite") String invite);

    List<Team> getTeamsByKeyword(@Param("keywords") List<String> keywords);

}
