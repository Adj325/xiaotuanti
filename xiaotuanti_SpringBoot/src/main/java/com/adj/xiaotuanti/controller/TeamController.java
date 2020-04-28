package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.Member;
import com.adj.xiaotuanti.pojo.Section;
import com.adj.xiaotuanti.pojo.Team;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.pojo.constants.MemberLevel;
import com.adj.xiaotuanti.pojo.dto.TeamDto;
import com.adj.xiaotuanti.service.MemberService;
import com.adj.xiaotuanti.service.SectionService;
import com.adj.xiaotuanti.service.TeamService;
import org.nlpcn.commons.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class TeamController {
    private final TeamService teamService;
    private final MemberService memberService;
    private final SectionService sectionService;

    @Autowired
    public TeamController(TeamService teamService, MemberService memberService, SectionService sectionService) {
        this.teamService = teamService;
        this.memberService = memberService;
        this.sectionService = sectionService;
    }

    private HashMap<String, Object> toMap(Team team) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", team.getId());
        hashMap.put("invite", team.getInvite());
        hashMap.put("owner", team.getUserOwner().getRealName());
        hashMap.put("introduction", team.getIntroduction());
        hashMap.put("name", team.getName());
        hashMap.put("memberNum", memberService.getMembersByTeamId(team.getId()).size());
        return hashMap;
    }

    private HashMap<String, Object>[] toMapArray(List<Team> teamList) {
        HashMap<String, Object>[] hashMaps = new HashMap[teamList.size()];
        for (int index = 0; index < teamList.size(); index++) {
            Team team = teamList.get(index);
            hashMaps[index] = toMap(team);
        }
        return hashMaps;
    }


    @PostMapping(value = "api/teams")
    @ResponseBody // 建立团体：创建一个团体、创建一个部门、创建一个成员
    public HashMap<String, Object> api_createTeam(@RequestAttribute("user") User user,
                                                  @RequestBody HashMap<String, Object> map) {
        HashMap<String, Object> result = new HashMap<>();
        try {

            if (teamService.getTeamsUserCreated(user.getId()).size() >= 5) {
                result.put("code", -1);
                result.put("msg", "每人最多创建5个团体");
                return result;
            }
            // 创建一个团体
            Team team = new Team(map);
            if (StringUtil.isBlank(team.getName())) {
                result.put("code", -1);
                result.put("msg", "团体名称为空");
                return result;
            }
            if (team.getName().length() > 20) {
                result.put("code", -1);
                result.put("msg", "团体名称过长");
                return result;
            }
            if (team.getIntroduction().length() > 256) {
                result.put("code", -1);
                result.put("msg", "团体介绍过长");
                return result;
            }
            team.setUserOwner(user);
            teamService.addTeam(team);
            // 创建一个部门
            Section section = new Section();
            section.setTeam(team);
            sectionService.addSection(section);
            // 创建一个成员
            Member member = new Member();
            member.setTeam(team);
            member.setUser(team.getUserOwner());
            member.setSection(section);
            member.setLevel(MemberLevel.TEAM_OWNER);
            memberService.addMember(member);
            result.put("code", 1);
            result.put("msg", "创建成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", -1);
            result.put("msg", "创建失败");
        }
        return result;
    }

    @GetMapping(value = "api/teams/{teamId}")
    @ResponseBody // 获取团体：根据teamId
    public HashMap<String, Object> api_getTeamById(@RequestAttribute("user") User user,
                                                   @PathVariable String teamId) {
        Team team = teamService.getTeamById(teamId);
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> dataMap = toMap(team);
        result.put("data", dataMap);
        if (member != null) {
            result.put("memberId", member.getId());
            result.put("permission", member.getLevel().ordinal());
            result.put("code", "1");
            result.put("msg", "获取成功");
        } else {
            result.put("permission", -1);
            result.put("code", "-1");
            result.put("msg", "获取失败");
        }
        return result;
    }

    @GetMapping(value = "api/teams")
    @ResponseBody // 获取团体
    public HashMap<String, Object> api_getTeams(@RequestAttribute("user") User user,
                                                @RequestParam("type") String type,
                                                @RequestParam(value = "keyword", required = false) String keyword,
                                                @RequestParam(value = "invite", required = false) String invite,
                                                @RequestParam(value = "teamId", required = false) String teamId) {
        System.out.println("type: " + type);
        switch (type) {
            case "join":
                return getTeamsUserJoin(user);
            case "create":
                return getTeamsUserCreate(user);
            case "invite":
                return getTeamsByInvite(user, invite);
            case "keyword":
                return getTeamsByKeyword(keyword);
            case "user":
                return getTeamsByUser(user);
            case "teamId":
                return getTeamByTeamId(user, teamId);
            default:
                HashMap<String, Object> result = new HashMap<>();
                result.put("code", "-1");
                result.put("msg", "type 错误");
                return result;
        }
    }

    private HashMap<String, Object> getTeamByTeamId(User user, String teamId) {
        HashMap<String, Object> result = new HashMap<>();
        Team team = teamService.getTeamById(teamId);
        if (team == null) {
            result.put("code", -1);
            result.put("msg", "团体不存在");
            return result;
        }
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        if (member == null) {
            result.put("permission", -1);
        } else {
            result.put("permission", member.getLevel().ordinal());
        }
        result.put("code", 1);
        result.put("msg", "获取成功");
        result.put("data", toMap(team));
        return result;
    }

    // 获取用户加入的团体
    private HashMap<String, Object> getTeamsUserJoin(@RequestAttribute("user") User user) {
        HashMap<String, Object> result = new HashMap<>();
        List<Team> teams = teamService.getTeamsUserJoined(user.getId());
        result.put("code", "1");
        result.put("msg", "获取成功");
        result.put("data", toMapArray(teams));
        return result;
    }

    // 获取用户创建的团体
    private HashMap<String, Object> getTeamsUserCreate(@RequestAttribute("user") User user) {
        HashMap<String, Object> result = new HashMap<>();
        List<Team> teams = teamService.getTeamsUserCreated(user.getId());
        result.put("code", "1");
        result.put("msg", "获取成功");
        result.put("data", toMapArray(teams));
        return result;
    }

    // 获取用户加入的团体
    private HashMap<String, Object> getTeamsByUser(@RequestAttribute("user") User user) {
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        List<Team> teamsUserCreate = teamService.getTeamsUserCreated(user.getId());
        List<Team> teamsUserJoined = teamService.getTeamsUserJoined(user.getId());
        dataMap.put("create", toMapArray(teamsUserCreate));
        dataMap.put("join", toMapArray(teamsUserJoined));
        result.put("code", "1");
        result.put("msg", "获取成功");
        result.put("data", dataMap);
        return result;
    }

    // 根据 invite 获取团体
    private HashMap<String, Object> getTeamsByInvite(User user, String invite) {
        HashMap<String, Object> result = new HashMap<>();
        if (invite == null || invite.equals("")) {
            result.put("code", "-1");
            result.put("msg", "邀请码为空");
            result.put("data", null);
        } else {
            List<Team> teamList = teamService.getTeamsByInvite(invite);
            result.put("code", "1");
            result.put("msg", "获取成功");
            HashMap<String, Object>[] hashMaps = toMapArray(teamList);
            for (HashMap<String, Object> hashMap : hashMaps) {
                String teamId = (String) hashMap.get("id");
                Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
                if (member == null) {
                    hashMap.put("join", false);
                } else {
                    hashMap.put("join", true);
                }
            }
            result.put("data", hashMaps);
        }
        return result;
    }

    // 根据 关键词 获取团体
    private HashMap<String, Object> getTeamsByKeyword(String keyword) {
        HashMap<String, Object> result = new HashMap<>();
        if (StringUtil.isBlank(keyword)) {
            result.put("code", "-1");
            result.put("msg", "邀请码为空");
            result.put("data", null);
        } else {
            List<Team> teamList = teamService.getTeamsByKeyword(keyword);
            result.put("code", "1");
            result.put("msg", "获取成功");
            result.put("data", toMapArray(teamList));
        }
        return result;
    }

    @PutMapping(value = "api/teams/{teamId}")
    @ResponseBody // 更新团体：根据teamId
    public HashMap<String, Object> updateTeamByTeamId(@RequestAttribute("user") User user,
                                                      @PathVariable String teamId,
                                                      @RequestBody TeamDto teamDto) {
        HashMap<String, Object> result = new HashMap<>();
        Team team = teamService.getTeamById(teamId);
        if (team == null) {
            result.put("code", -1);
            result.put("msg", "团体不存在");
            return result;
        }
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        // 只有团主和管理员才可以更改团体信息
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            team.setName(teamDto.getName());
            team.setInvite(teamDto.getInvite());
            team.setIntroduction(teamDto.getIntroduction());
            teamService.updateTeam(team);
            result.put("code", 1);
            result.put("msg", "成功更新");
        } else {
            result.put("code", -1);
            result.put("msg", "无权更新");
        }
        return result;
    }

    @PostMapping(value = "api/teams/delete/{teamId}")
    @ResponseBody // 销毁团体：根据teamId
    public HashMap<String, Object> api_destroyTeam(@RequestAttribute("user") User user,
                                                   @PathVariable String teamId) {
        HashMap<String, Object> result = new HashMap<>();
        Member cruMember = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        if (cruMember.getLevel() == MemberLevel.TEAM_OWNER) {
            teamService.deleteTeamById(teamId);
            result.put("code", 1);
            result.put("msg", "成功销毁！");
        } else {
            result.put("code", -1);
            result.put("msg", "无权操作！");
        }
        return result;
    }
}