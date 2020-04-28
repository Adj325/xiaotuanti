package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.*;
import com.adj.xiaotuanti.pojo.constants.MemberLevel;
import com.adj.xiaotuanti.pojo.dto.WillingDto;
import com.adj.xiaotuanti.service.ActivityService;
import com.adj.xiaotuanti.service.MemberService;
import com.adj.xiaotuanti.service.UserService;
import com.adj.xiaotuanti.service.WillingService;
import org.nlpcn.commons.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class WillingController {

    private final UserService userService;
    private final MemberService memberService;
    private final WillingService willingService;
    private final ActivityService activityService;

    @Autowired
    public WillingController(UserService userService, MemberService memberService, ActivityService activityService, WillingService willingService) {
        this.userService = userService;
        this.memberService = memberService;
        this.activityService = activityService;
        this.willingService = willingService;
    }

    @RequestMapping(value = "api/willing/isSignUp")
    public HashMap<String, Object> isSignUp(@RequestAttribute("user") User user, @RequestBody WillingDto dto) {
        String activityId = dto.getActivityId();
        Willing willing = willingService.getWillingByUserIdAndActivityId(user.getId(), activityId);
        willingService.deleteWillingByUserIdAndActivityId(user.getId(), activityId);
        HashMap<String, Object> result = new HashMap<>();

        if (willing == null) {
            result.put("code", 1);
            result.put("msg", "默认状态");
            result.put("signStatus", 0);
        } else {
            result.put("code", 1);
            if (willing.getType() == 0) {
                result.put("signStatus", -1);
                result.put("msg", "已请假");
            } else {
                result.put("signStatus", 1);
                result.put("msg", "已报名");
            }
        }
        return result;
    }

    @RequestMapping(value = "api/willing/signUp")
    public HashMap<String, Object> addWilling(@RequestAttribute("user") User user, @RequestBody WillingDto dto) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            String activityId = dto.getActivityId();
            Activity activity = activityService.getActivityById(activityId);
            Willing willing = willingService.getWillingByUserIdAndActivityId(user.getId(), activityId);
            if (willing != null) {
                result.put("code", 1);
                result.put("msg", "已取消报名");
                willingService.deleteWillingByUserIdAndActivityId(user.getId(), activityId);
                result.put("signStatus", 0);
                return result;
            } else {
                willing = new Willing();
                willing.setActivity(activity);
                willing.setUserOwner(user);
                if (dto.getWilling()) {
                    willing.setType(1);
                } else {
                    willing.setType(0);
                }
                willingService.addWilling(willing);
                result.put("code", 1);

                if (dto.getWilling()) {
                    willing.setType(1);
                    result.put("msg", "报名成功");
                    result.put("signStatus", 1);
                } else {
                    willing.setType(0);
                    result.put("msg", "请假成功");
                    result.put("signStatus", -1);
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", -1);
            result.put("msg", "报名失败");
            result.put("isSignUp", false);
            return result;
        }
    }

    @GetMapping("api/willing/users")
    public HashMap<String, Object> getWillingUsers(@RequestAttribute("user") User user, WillingDto dto){
        HashMap<String, Object> result = new HashMap<>();
        String activityId = dto.getActivityId();
        Activity activity = activityService.getActivityById(activityId);
        Team team = activity.getTeam();
        Member curMember = memberService.getMemberByUserIdAndTeamId(user.getId(), team.getId());
        if (curMember != null && curMember.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()){
            List<User> signUsers = userService.getWillingUsersByActivityIdAndWillingType(activityId, 1);
            List<HashMap> willingUsers = new ArrayList<>();
            for(User signUser: signUsers){
                HashMap<String, Object> userMap = new HashMap<>();
                userMap.put("type", "sign");
                userMap.put("id", signUser.getId());
                userMap.put("avatarUrl", signUser.getAvatarUrl());
                userMap.put("name", signUser.getNickName());
                if (StringUtil.isNotBlank(signUser.getRealName())){
                    userMap.put("name", signUser.getRealName());
                }
                if (memberService.getMemberByUserIdAndTeamId(signUser.getId(), team.getId()) == null){
                    userMap.put("isMember", false);
                }else{
                    userMap.put("isMember", true);
                }
                willingUsers.add(userMap);
            }
            List<User> leaveUsers = userService.getWillingUsersByActivityIdAndWillingType(activityId, 0);
            for(User leaveUser: leaveUsers){
                HashMap<String, Object> userMap = new HashMap<>();
                userMap.put("type", "leave");
                userMap.put("id", leaveUser.getId());
                userMap.put("avatarUrl", leaveUser.getAvatarUrl());
                userMap.put("name", leaveUser.getNickName());
                if (StringUtil.isNotBlank(leaveUser.getRealName())){
                    userMap.put("name", leaveUser.getRealName());
                }
                if (memberService.getMemberByUserIdAndTeamId(leaveUser.getId(), team.getId()) == null){
                    userMap.put("isMember", false);
                }else{
                    userMap.put("isMember", true);
                }
                willingUsers.add(userMap);
            }
            result.put("willingUsers", willingUsers);
            result.put("code", 1);
            result.put("msg", "获取成功");
        }else{
            result.put("code", -1);
            result.put("msg", "无权获取");
        }
        return result;
    }
}
