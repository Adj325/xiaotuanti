package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.Member;
import com.adj.xiaotuanti.pojo.Section;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.pojo.constants.MemberLevel;
import com.adj.xiaotuanti.pojo.dto.ManagerDto;
import com.adj.xiaotuanti.service.MemberService;
import com.adj.xiaotuanti.service.Relation_Activity_UserService;
import com.adj.xiaotuanti.service.SectionService;
import com.adj.xiaotuanti.service.TeamService;
import org.nlpcn.commons.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class MemberController {
    private final TeamService teamService;
    private final MemberService memberService;
    private final Relation_Activity_UserService relationActivityUserService;
    private final SectionService sectionService;

    @Autowired
    public MemberController(TeamService teamService, MemberService memberService, Relation_Activity_UserService relationActivityUserService, SectionService sectionService) {
        this.teamService = teamService;
        this.memberService = memberService;
        this.relationActivityUserService = relationActivityUserService;
        this.sectionService = sectionService;
    }

    private HashMap<String, Object> toMap(Member member) {
        HashMap<String, Object> hashMap = new HashMap<>();
        User user = member.getUser();
        hashMap.put("id", member.getId());
        String name = user.getNickName();
        if (StringUtil.isNotBlank(user.getRealName())) {
            name = user.getRealName();
        }
        hashMap.put("name", name);
        hashMap.put("avatarUrl", user.getAvatarUrl());
        if (member.getSection() != null) {
            Section section = member.getSection();
            hashMap.put("section", section.getId());
            hashMap.put("sectionId", section.getId());
            hashMap.put("sectionName", section.getName());
        }
        hashMap.put("job", member.getJob());
        hashMap.put("grade", user.getGrade());
        hashMap.put("gender", user.getGender());
        hashMap.put("stuno", user.getStuno());
        hashMap.put("phone", member.getPhone());
        hashMap.put("address", member.getAddress());
        hashMap.put("weixin", member.getWeixin());
        hashMap.put("qq", member.getQq());
        hashMap.put("email", member.getEmail());
        hashMap.put("dormitory", member.getDormitory());
        return hashMap;
    }

    /**
     * 非本团体的访问用户：-1
     * 本团体的访问用户：0
     * 成员本身：1
     * 本团体的管理员：2
     */

    @GetMapping(value = "api/permission")
    @ResponseBody // 获取成员：根据memberId
    public HashMap getPermissionLevel(@RequestAttribute("user") User user,
                                      @RequestParam String teamId) {
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        HashMap<String, Object> result = new HashMap<>();
        if (member == null) {
            result.put("msg", "非团体成员");
            result.put("level", -1);
            result.put("member", null);
        } else {
            result.put("msg", "团体成员");
            result.put("level", member.getLevel().ordinal());
            result.put("member", toMap(member));
        }
        return result;
    }

    /**
     * 非本团体的访问用户：-1
     * 本团体的访问用户：0
     * 成员本身：1
     * 本团体的管理员：2
     */
    @GetMapping(value = "api/member/{memberId}")
    @ResponseBody // 获取成员：根据memberId
    public HashMap getMemberByMemberId(@RequestAttribute("user") User user,
                                       @PathVariable String memberId) {
        Member targetMember = memberService.getMemberById(memberId);
        Member curMember = memberService.getMemberByUserIdAndTeamId(user.getId(), targetMember.getTeam().getId());
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> dataMap;
        // 权限判断
        if (curMember != null) {
            dataMap = toMap(targetMember);
            if (memberId.equals(curMember.getId())) {
                // 成员本人 1
                dataMap.put("special", targetMember.getSpecial());
                result.put("permission", 1);
            } else if (curMember.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
                // 管理员 2
                dataMap.put("special", targetMember.getSpecial());
                result.put("permission", 2);
            } else {
                // 本团体的其它成员 0
                result.put("permission", 0);
            }
            result.put("data", dataMap);
        } else {
            dataMap = new HashMap<>();
            dataMap.put("name", targetMember.getUser().getRealName());
            result.put("permission", -1);
        }
        return result;
    }


    /**
     * 非本团体的访问用户：-1
     * 本团体的访问用户：0
     * 成员本身：1
     * 本团体的管理员：2
     */

    @GetMapping(value = "api/member")
    @ResponseBody // 获取成员：根据memberId
    public HashMap getMyMemberInfo(@RequestAttribute("user") User user,
                                   @RequestParam String teamId) {
        Member curMember = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        // 权限判断
        if (curMember != null) {
            dataMap.put("memberId", curMember.getId());
            dataMap.put("memberName", curMember.getUser().getRealName());
            dataMap.put("avatarUrl", curMember.getUser().getAvatarUrl());
            result.put("data", dataMap);
            result.put("code", "1");
            result.put("msg", "成功获取成员信息！");
        } else {
            dataMap.put("memberId", "");
            dataMap.put("memberName", "");
            dataMap.put("avatarUrl", "");
            result.put("data", dataMap);
            result.put("code", "-1");
            result.put("msg", "成功获取成员信息！");
        }
        return result;
    }


    @GetMapping(value = "api/members")
    @ResponseBody // 获取成员：根据teamId
    public HashMap getMembersByTeamId(
            @RequestAttribute("user") User user,
            @RequestParam String type,
            @RequestParam(required = false) String teamId,
            @RequestParam(required = false) String memberId) {
        HashMap<String, Object> result = new HashMap<>();
        Member curMember;
        switch (type) {
            case "teamId":
                curMember = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
                List<Member> memberList = memberService.getMembersByTeamId(teamId);
                HashMap<String, Object>[] dataMap = new HashMap[memberList.size()];
                for (int index = 0; index < memberList.size(); index++) {
                    Member member = memberList.get(index);
                    User memberUser = member.getUser();

                    dataMap[index] = new HashMap<String, Object>();
                    dataMap[index].put("id", member.getId());
                    dataMap[index].put("name", member.getUser().getRealName());
                    if (curMember != null) {
                        if (member.getSection() != null) {
                            dataMap[index].put("sectionId", member.getSection().getId());
                            dataMap[index].put("sectionName", member.getSection().getName());
                        } else {
                            dataMap[index].put("sectionId", -1);
                            dataMap[index].put("sectionName", "无部门");
                        }
                        dataMap[index].put("job", member.getJob());
                        dataMap[index].put("phone", member.getPhone());
                        dataMap[index].put("stuno", member.getUser().getStuno());
                        dataMap[index].put("address", member.getAddress());
                        dataMap[index].put("level", member.getLevel().ordinal());
                        dataMap[index].put("grade", memberUser.getGrade());
                        dataMap[index].put("gender", memberUser.getGender());
                        dataMap[index].put("avatarUrl", memberUser.getAvatarUrl());
                        dataMap[index].put("memberLevel", member.getLevel().ordinal());
                        dataMap[index].put("recordNumber", relationActivityUserService.getRecordNumberByMemberId(member.getId()));
                    }
                }
                if (curMember == null) {
                    result.put("level", -1);
                } else {
                    result.put("level", curMember.getLevel().ordinal());
                }
                result.put("code", 1);
                result.put("data", dataMap);
                result.put("msg", "获取成功");
                return result;
            case "memberId":
                Member targetMember = memberService.getMemberById(memberId);
                if (targetMember == null) {
                    result.put("code", -1);
                    result.put("msg", "获取失败");
                    return result;
                }
                teamId = targetMember.getTeam().getId();
                curMember = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
                result.put("code", 1);
                result.put("self", false);
                result.put("editable", false);
                if (curMember == null) {
                    result.put("level", -1);
                    result.put("memberLevel", targetMember.getLevel().ordinal());
                    result.put("data", targetMember);
                    return result;
                } else {
                    if (curMember.getId().equals(memberId)) {
                        result.put("self", true);
                        result.put("editable", true);
                    }
                    if (curMember.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
                        result.put("editable", true);
                    }
                    result.put("memberLevel", targetMember.getLevel().ordinal());
                    result.put("level", curMember.getLevel().ordinal());
                    result.put("data", toMap(targetMember));
                    return result;
                }

            default:
                result.put("code", -1);
                result.put("msg", "type 有误");
                result.put("level", -1);
                return result;
        }
    }

    // 更新成员：根据memberId
    @PostMapping(value = "api/members/delete/{memberId}")
    @ResponseBody
    public HashMap deleteMemberByMemberId(
            @RequestAttribute("user") User user,
            @PathVariable("memberId") String memberId) {
        HashMap<String, Object> result = new HashMap<>();
        Member targetMember = memberService.getMemberById(memberId);
        if (targetMember == null) {
            result.put("code", -1);
            result.put("msg", "成员id不存在");
            return result;
        }
        String teamId = targetMember.getTeam().getId();
        Member curMember = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        // 权限判断
        if (curMember != null) {
            // 成员本人
            if (memberId.equals(curMember.getId())) {
                if (curMember.getLevel() == MemberLevel.TEAM_OWNER){
                    result.put("code", -1);
                    result.put("msg", "您是团主，无法退团。");
                    return result;
                }else{
                    memberService.deleteMemberById(memberId);
                    result.put("code", 1);
                    result.put("msg", "退团成功。");
                    return result;
                }
            } else if (curMember.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
                // 管理员 2
                memberService.deleteMemberById(memberId);
                result.put("code", 1);
                result.put("msg", "退团成功。");
                return result;
            }
            result.put("code", 1);
            result.put("msg", "更新成功");
            return result;
        } else {
            result.put("code", -1);
            result.put("msg", "无权操作");
            return result;
        }
    }

    @PutMapping(value = "api/members/{memberId}")
    @ResponseBody
    public HashMap updateMemberByMemberId(
            @RequestAttribute("user") User user,
            @PathVariable("memberId") String memberId,
            @RequestBody HashMap<String, Object> map) {
        String teamId = (String) map.get("teamId");
        HashMap<String, Object> result = new HashMap<>();
        Member curMember = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        Member targetMember = new Member(map);
        // 权限判断
        if (curMember != null) {
            if (memberId.equals(curMember.getId())) {
                // 成员本人 0
                memberService.updateMember(targetMember);
            } else if (curMember.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
                // 管理员 2
                memberService.updateMember(targetMember);
            }
            result.put("code", 0);
            result.put("msg", "更新成功");
        } else {
            result.put("code", -1);
            result.put("msg", "无权更新");
        }
        return result;
    }

    @PutMapping(value = "api/managers")
    @ResponseBody // 设置管理员：根据teamId
    public HashMap updateManagersByTeamId(
            @RequestAttribute("user") User user,
            @RequestBody ManagerDto managerDto) {
        HashMap<String, Object> result = new HashMap<>();
        String teamId = managerDto.getTeamId();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        // 只有团主才可以更改团体信息
        if (member != null && member.getLevel() == MemberLevel.TEAM_OWNER) {
            // 撤销所有人的管理员权限
            memberService.revokeManagerPermissionByTeamId(teamId);
            // 设置管理员权限
            List<String> managerMemberIds = managerDto.getManagerMemberIds();
            for (String memberId : managerMemberIds) {
                Member tempMember = memberService.getMemberById(memberId);
                // 权限2是团主，跳过团主
                if (tempMember.getLevel() == MemberLevel.TEAM_OWNER) {
                    continue;
                }
                tempMember.setLevel(MemberLevel.TEAM_MANAGER);
                memberService.updateMember(tempMember);
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