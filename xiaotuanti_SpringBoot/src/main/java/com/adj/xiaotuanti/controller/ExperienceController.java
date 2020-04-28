
package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.*;
import com.adj.xiaotuanti.pojo.constants.MemberLevel;
import com.adj.xiaotuanti.pojo.dto.ExperienceDto;
import com.adj.xiaotuanti.service.ActivityService;
import com.adj.xiaotuanti.service.ExperienceService;
import com.adj.xiaotuanti.service.MemberService;
import com.adj.xiaotuanti.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class ExperienceController {

    private final TeamService teamService;
    private final ActivityService activityService;
    private final MemberService memberService;
    private final ExperienceService experienceService;

    @Autowired
    public ExperienceController(TeamService teamService, ActivityService activityService, MemberService memberService, ExperienceService experienceService) {
        this.teamService = teamService;
        this.activityService = activityService;
        this.memberService = memberService;
        this.experienceService = experienceService;
    }

    private HashMap<String, Object> toMap(Experience experience) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", experience.getId());
        hashMap.put("authorId", experience.getUserAuthor().getId());
        hashMap.put("authorAvatarUrl", experience.getUserAuthor().getAvatarUrl());
        hashMap.put("authorName", experience.getUserAuthor().getRealName().substring(0, 3));
        hashMap.put("authorMemberId", experience.getMemberAuthor().getId());
        hashMap.put("content", experience.getContent());
        hashMap.put("postTime", experience.getPostTime());
        if (experience.getActivity() != null) {
            hashMap.put("withActivity", experience.getPostTime());
            hashMap.put("activityId", experience.getActivity().getId());
            hashMap.put("activityName", experience.getActivity().getName());
        } else {
            hashMap.put("withActivity", false);
        }
        return hashMap;
    }

    private HashMap<String, Object>[] toMapArray(List<Experience> experienceList) {
        HashMap<String, Object>[] hashMaps = new HashMap[experienceList.size()];
        for (int index = 0; index < experienceList.size(); index++) {
            Experience experience = experienceList.get(index);
            hashMaps[index] = toMap(experience);
        }
        return hashMaps;
    }

    // 添加Experience
    @PostMapping(value = "api/experience")
    @ResponseBody
    public HashMap addExperience(
            @RequestAttribute User user,
            @RequestBody ExperienceDto experienceDto) {
        HashMap<String, Object> result = new HashMap<>();
        String type = experienceDto.getType();

        String teamId;
        Team team;
        Member member;
        switch (type) {
            case "activityId":
                String activityId = experienceDto.getActivityId();
                Activity activity = activityService.getActivityById(activityId);
                if (activity == null) {
                    result.put("code", -1);
                    result.put("msg", "活动不存在");
                    return result;
                }
                team = activity.getTeam();
                member = memberService.getMemberByUserIdAndTeamId(user.getId(), team.getId());
                if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
                    Experience experience = new Experience();
                    experience.setContent(experienceDto.getContent());
                    experience.setActivity(activity);
                    experience.setUserAuthor(user);
                    experience.setTeam(team);
                    experience.setPostTime(new Date());
                    experienceService.addExperience(experience);
                    result.put("code", 1);
                    result.put("msg", "添加成功");
                    return result;
                } else {
                    result.put("code", -1);
                    result.put("msg", "无权添加");
                    return result;
                }
            default:
                teamId = experienceDto.getTeamId();
                team = teamService.getTeamById(teamId);
                member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
                if (member != null) {
                    Experience experience = new Experience();
                    experience.setContent(experienceDto.getContent());
                    experience.setUserAuthor(user);
                    experience.setTeam(team);
                    experience.setPostTime(new Date());
                    experienceService.addExperience(experience);
                    result.put("code", 1);
                    result.put("msg", "添加成功");
                    return result;
                } else {
                    result.put("code", -1);
                    result.put("msg", "无权添加");
                    return result;
                }
        }
    }

    // 更新Experience
    @PutMapping(value = "api/experience/{experienceId}")
    @ResponseBody
    public HashMap updateExperience(
            @RequestAttribute User user,
            @PathVariable String experienceId,
            @RequestBody ExperienceDto experienceDto) {
        HashMap<String, Object> result = new HashMap<>();
        String teamId = experienceDto.getTeamId();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            Experience experience = new Experience();
            experience.setId(experienceId);
            experience.setContent(experienceDto.getContent());
            experienceService.updateExperience(experience);
            result.put("code", 1);
            result.put("msg", "更新成功");
        } else {
            result.put("code", -1);
            result.put("msg", "无权更新");
        }
        return result;
    }

    @PostMapping(value = "api/experience/delete")
    @ResponseBody // 删除Experience
    public HashMap deleteExperience(
            @RequestAttribute User user,
            @RequestBody ExperienceDto experienceDto) {
        HashMap<String, Object> result = new HashMap<>();
        Experience experience = experienceService.getExperienceById(experienceDto.getId());
        if (experience == null) {
            result.put("code", -1);
            result.put("msg", "物资无效");
            return result;
        }
        String teamId = experience.getTeam().getId();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            experienceService.deleteExperienceById(experienceDto.getId());
            result.put("code", 1);
            result.put("msg", "删除成功");
        } else {
            result.put("code", -1);
            result.put("msg", "无权删除");
        }
        return result;
    }

    // 获取Experience
    @GetMapping(value = "api/experience")
    @ResponseBody
    public HashMap getExperienceByTeamId(@RequestAttribute User user,
                                         @RequestParam String type,
                                         @RequestParam(required = false) String experienceId,
                                         @RequestParam(required = false) String teamId) {
        HashMap<String, Object> result = new HashMap<>();
        Member member;
        switch (type) {
            case "experienceId":
                member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
                if (member == null) {
                    result.put("permission", -1);
                } else {
                    result.put("data", toMap(experienceService.getExperienceById(experienceId)));
                    result.put("permission", member.getLevel().ordinal());
                }
                break;
            case "teamId":
                member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
                if (member == null) {
                    result.put("permission", -1);
                } else {
                    List<Experience> experienceList = experienceService.getExperienceByTeamId(teamId);
                    result.put("data", toMapArray(experienceList));
                    result.put("permission", member.getLevel().ordinal());
                }
            default:
                break;
        }
        return result;
    }
}
