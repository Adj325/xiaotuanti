package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Team;

import java.util.List;

public interface TeamService {

    // 根据id获取团体
    Team getTeamById(String teamId);

    // 根据邀请码获取多个团体
    List<Team> getTeamsByInvite(String invite);

    List<Team> getTeamsByKeyword(String keyword);

    // 获取用户创建的所有团体
    List<Team> getTeamsUserCreated(String userId);

    // 获取用户的加入的所有团体
    List<Team> getTeamsUserJoined(String userId);

    // 添加团体
    void addTeam(Team team);

    // 更新团体
    void updateTeam(Team team);

    void deleteTeamById(String teamId);
}
