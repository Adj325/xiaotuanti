package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.*;
import com.adj.xiaotuanti.pojo.constants.MemberLevel;
import com.adj.xiaotuanti.pojo.dto.ApplyDto;
import com.adj.xiaotuanti.service.ApplyService;
import com.adj.xiaotuanti.service.MemberService;
import com.adj.xiaotuanti.service.NoticeService;
import com.adj.xiaotuanti.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class ApplyController {

    private final TeamService teamService;
    private final ApplyService applyService;
    private final NoticeService noticeService;
    private final MemberService memberService;

    @Autowired
    public ApplyController(TeamService teamService, ApplyService applyService, NoticeService noticeService, MemberService memberService) {
        this.teamService = teamService;
        this.applyService = applyService;
        this.noticeService = noticeService;
        this.memberService = memberService;
    }

    private HashMap[] toMapArray(List<Apply> applyList) {
        HashMap[] hashMaps = new HashMap[applyList.size()];
        for (int i = 0; i < applyList.size(); i++) {
            hashMaps[i] = new HashMap();
            Apply apply = applyList.get(i);
            User userAuthor = apply.getUserAuthor();
            hashMaps[i].put("id", apply.getId());
            hashMaps[i].put("authorId", userAuthor.getId());
            hashMaps[i].put("authorName", userAuthor.getRealName());
            hashMaps[i].put("authorAvatarUrl", userAuthor.getAvatarUrl());
        }
        return hashMaps;
    }


    @PostMapping(value = "api/applies")
    @ResponseBody // 添加申请
    public HashMap addApply(@RequestAttribute User user,
                            @RequestBody ApplyDto applyDto) {
        String teamId = applyDto.getTeamId();
        Team team = teamService.getTeamById(teamId);
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        HashMap<String, Object> resultMap = new HashMap<>();
        if (team == null) {
            resultMap.put("code", -1);
            resultMap.put("msg", "不存在团体");
        }
        if (member == null) {
            Apply apply = new Apply();
            apply.setUserAuthor(user);
            apply.setTeam(team);
            Apply oldApply = applyService.getApplyByTeamIdAndUserId(apply);
            if (oldApply == null) {
                applyService.addApply(apply);
                resultMap.put("code", 1);
                resultMap.put("msg", "已成功发起申请！");
            } else {
                resultMap.put("code", -1);
                resultMap.put("msg", "请不要重复发起申请！");
            }
        } else {
            resultMap.put("code", -1);
            resultMap.put("msg", "你早已加入该团体");
        }
        return resultMap;
    }


    @GetMapping(value = "api/applies")
    @ResponseBody // 获取申请：根据teamId
    public HashMap getAppliesByTeamId(
            @RequestAttribute User user,
            @RequestParam String teamId) {
        HashMap<String, Object> resultMap = new HashMap<>();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        // 只有成员才可以看到申请
        if (member != null) {
            List<Apply> applyList = applyService.getAppliesByTeamId(teamId);
            resultMap.put("data", toMapArray(applyList));
            resultMap.put("msg", "获取申请");
        }
        return resultMap;
    }

    @PutMapping(value = "api/applies/{applyId}")
    @ResponseBody // 更新申请：根据teamId
    public HashMap updateApplyByApplyId(
            @RequestAttribute User user,
            @PathVariable String applyId,
            @RequestBody ApplyDto applyDto) {
        HashMap<String, Object> resultMap = new HashMap<>();
        Team team = teamService.getTeamById(applyDto.getTeamId());
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), applyDto.getTeamId());

        // 0未处理，1接受，2拒绝
        // 只有团主和管理员才可以更改申请信息
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            Apply apply = applyService.getApplyById(applyId);
            Notice notice = new Notice();
            notice.setReceiver(apply.getUserAuthor());
            notice.setPostTime(new Date());
            switch (applyDto.getResult()) {
                case "1": {
                    // 接受
                    String noticeContent = String.format("恭喜你，“%s”同意了你加入申请！", team.getName());
                    notice.setContent(noticeContent);
                    noticeService.addNotice(notice);
                    applyService.deleteApplyById(applyId);
                    Member newMember = new Member();
                    newMember.setLevel(MemberLevel.TEAM_MEMBER);
                    newMember.setUser(apply.getUserAuthor());
                    newMember.setTeam(team);
                    memberService.addMember(newMember);
                    resultMap.put("msg", "添加成功");
                    break;
                }
                case "2": {
                    // 拒绝
                    String noticeContent = String.format("真遗憾，“%s”拒绝了你加入申请！", team.getName());
                    notice.setContent(noticeContent);
                    noticeService.addNotice(notice);
                    applyService.deleteApplyById(applyId);
                    resultMap.put("code", 0);
                    resultMap.put("msg", "拒绝成功");
                    break;
                }
                default:
                    resultMap.put("code", -1);
                    resultMap.put("msg", applyDto.getResult() + "无效");
                    break;
            }
        } else {
            resultMap.put("code", -1);
            resultMap.put("msg", "无权操作");
        }
        return resultMap;
    }
}