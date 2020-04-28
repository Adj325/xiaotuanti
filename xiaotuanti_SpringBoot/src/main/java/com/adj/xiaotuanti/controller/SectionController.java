package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.Member;
import com.adj.xiaotuanti.pojo.Section;
import com.adj.xiaotuanti.pojo.Team;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.pojo.constants.MemberLevel;
import com.adj.xiaotuanti.pojo.dto.SectionDto;
import com.adj.xiaotuanti.service.MemberService;
import com.adj.xiaotuanti.service.SectionService;
import com.adj.xiaotuanti.service.TeamService;
import com.alibaba.fastjson.JSON;
import org.nlpcn.commons.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SectionController {
    private final SectionService sectionService;
    private final TeamService teamService;
    private final MemberService memberService;

    @Autowired
    public SectionController(SectionService sectionService, TeamService teamService, MemberService memberService) {
        this.sectionService = sectionService;
        this.teamService = teamService;
        this.memberService = memberService;
    }

    private HashMap<String, Object>[] toMapArray(List<Section> sectionList) {
        HashMap<String, Object>[] hashMaps = new HashMap[sectionList.size()];
        for (int i = 0; i < sectionList.size(); i++) {
            hashMaps[i] = new HashMap<>();
            Section section = sectionList.get(i);
            hashMaps[i].put("id", section.getId());
            hashMaps[i].put("name", section.getName());
            hashMaps[i].put("memberNum", section.getMemberNum());
        }
        return hashMaps;
    }


    @GetMapping(value = "api/sections")
    @ResponseBody // 获取部门：根据teamId
    public String getSectionsByTeamId(
            @RequestAttribute("user") User user,
            @RequestParam String teamId) {
        List<Section> sectionList = sectionService.getSectionsByTeamId(teamId);
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        HashMap<String, Object> result = new HashMap<>();
        Map[] dataMap = toMapArray(sectionList);
        result.put("data", dataMap);
        if (member != null) {
            result.put("permission", member.getLevel().ordinal());
        } else {
            result.put("permission", -1);
        }
        String frontResult = JSON.toJSONString(result);
        System.out.println(frontResult);
        return frontResult;
    }


    @PutMapping(value = "api/sections")
    @ResponseBody // 更新部门：根据teamId
    public HashMap<String, Object> updateSectionsByTeamId(@RequestAttribute("user") User user,
                                                          @RequestBody SectionDto sectionDto) {
        HashMap<String, Object> result = new HashMap<>();
        String teamId = sectionDto.getTeamId();
        Team team = teamService.getTeamById(teamId);
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);

        // 只有团主和管理员才可以更改部门信息
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            // 创建或更新部门
            List<Section> sectionList = sectionDto.getSections();
            for (Section section : sectionList) {
                if (section == null)
                    continue;
                // 部门名字不能为空
                if (StringUtil.isNotBlank(section.getName())) {
                    if (StringUtil.isNotBlank(section.getId())) {
                        sectionService.updateSection(section);
                    } else {
                        Section tempSection = new Section();
                        tempSection.setName(section.getName());
                        tempSection.setTeam(team);
                        sectionService.addSection(tempSection);
                    }
                }
            }
            // 删除部门
            List<String> idsToRemove = sectionDto.getIdsToRemove();
            for (String tempSectionId : idsToRemove) {
                if (tempSectionId != null && !tempSectionId.equals("")) {
                    Section teampSection = sectionService.getSectionById(tempSectionId);
                    sectionService.deleteSection(teampSection);
                }
            }
            result.put("code", 1);
            result.put("msg", "更新成功");
        } else {
            result.put("code", -1);
            result.put("msg", "无权更新");
        }

        return result;
    }
}