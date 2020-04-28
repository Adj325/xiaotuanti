package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.TeamDAO;
import com.adj.xiaotuanti.pojo.Team;
import com.adj.xiaotuanti.service.MemberService;
import com.adj.xiaotuanti.service.SectionService;
import com.adj.xiaotuanti.service.TeamService;
import com.adj.xiaotuanti.util.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("teamService")
public class TeamServiceImpl implements TeamService {

    private final TeamDAO teamDAO;

    @Autowired
    public TeamServiceImpl(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    // 根据id获取团体
    public Team getTeamById(String teamId) {
        return teamDAO.getTeamById(teamId);
    }

    // 获取所有团体
    public List<Team> getAllTeams() {
        return teamDAO.getAllTeams();
    }

    // 根据 邀请码 获取多个团体
    public List<Team> getTeamsByInvite(String invite){
        return teamDAO.getTeamsByInvite(invite);
    }

    // 根据 关键词 获取多个团体
    public List<Team> getTeamsByKeyword(String keyword){
        return teamDAO.getTeamsByKeyword(WordUtils.getNlpSeg(keyword));
    }


    // 获取用户创建的所有团体
    public List<Team> getTeamsUserCreated(String userId) {
        return teamDAO.getTeamsUserCreated(userId);
    }

    // 获取用户的加入的所有团体
    public List<Team> getTeamsUserJoined(String userId) {
        return teamDAO.getTeamsUserJoined(userId);
    }

    // 添加团体
    public void addTeam(Team team) {
        // 创建一个团体
        teamDAO.addTeam(team);
    }

    // 更新团体
    public void updateTeam(Team team){
        Team tempTeam = getTeamById(team.getId());
        if(tempTeam != null){
            teamDAO.updateTeam(team);
        }
    }

    // 销毁团体
    public void deleteTeamById(String teamId){
        teamDAO.deleteTeamById(teamId);
    }
}
