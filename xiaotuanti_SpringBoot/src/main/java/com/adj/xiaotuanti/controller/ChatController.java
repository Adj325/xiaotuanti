package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.*;
import com.adj.xiaotuanti.pojo.constants.MemberLevel;
import com.adj.xiaotuanti.service.ActivityService;
import com.adj.xiaotuanti.service.ChatService;
import com.adj.xiaotuanti.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ChatController {

    private final ChatService chatService;
    private final MemberService memberService;
    private final ActivityService activityService;

    @Autowired
    public ChatController(ChatService chatService, MemberService memberService, ActivityService activityService) {
        this.chatService = chatService;
        this.memberService = memberService;
        this.activityService = activityService;
    }

    private HashMap toMap(Chat chat) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id", chat.getId());
        hashMap.put("member", chat.getMember());
        hashMap.put("content", chat.getContent());
        return hashMap;
    }

    private HashMap[] toMapArray(List<Chat> chatList) {
        HashMap<String, Object>[] hashMaps = new HashMap[chatList.size()];
        for (int index = 0; index < chatList.size(); index++) {
            Chat chat = chatList.get(index);
            User user = chat.getMember().getUser();
            Bin bin = chat.getBin();
            hashMaps[index] = new HashMap<String, Object>();
            hashMaps[index].put("id", chat.getId());
            hashMaps[index].put("memberId", chat.getMember().getId());
            hashMaps[index].put("memberName", user.getRealName());
            hashMaps[index].put("avatarUrl", user.getAvatarUrl());
            hashMaps[index].put("content", chat.getContent());
            hashMaps[index].put("postTime", chat.getPostTime());
            if (bin != null) {
                hashMaps[index].put("binId", bin.getId());
                hashMaps[index].put("binName", bin.getName());
                hashMaps[index].put("binType", bin.getType().split("/")[0]);
            } else {
                hashMaps[index].put("binId", "");
                hashMaps[index].put("binName", "");
                hashMaps[index].put("binType", "");
            }
        }
        return hashMaps;
    }

    @GetMapping(value = "api/chats")
    @ResponseBody // 获取申请：根据teamId
    public HashMap getAppliesByTeamId(@RequestAttribute("user") User user, @RequestParam String activityId) {
        HashMap<String, Object> result = new HashMap<>();
        Activity activity = activityService.getActivityById(activityId);
        if (activity == null){
            result.put("code", -1);
            result.put("msg", "活动不存在");
            result.put("permission", -1);
            return result;
        }
        String teamId = activity.getTeam().getId();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        // 只有成员才可以看到申请
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            List<Chat> chats = chatService.getChat100(activityId);
            result.put("data", toMapArray(chats));
            result.put("code", 1);
            result.put("msg", "成功获取聊天记录！");
            result.put("permission", member.getLevel().ordinal());
        } else {
            result.put("data", new ArrayList<>());
            result.put("code", -1);
            result.put("msg", "无权获取聊天记录！");
            result.put("permission", "-1");
        }
        return result;
    }

}
