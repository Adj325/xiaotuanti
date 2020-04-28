package com.adj.xiaotuanti.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.adj.xiaotuanti.pojo.*;
import com.adj.xiaotuanti.pojo.constants.MemberLevel;
import com.adj.xiaotuanti.service.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;


@Service
public class SocketHandler implements WebSocketHandler {
    @Autowired
    private BinService binService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ActivityService activityService;

    private final static Logger log = Logger.getLogger(SocketHandler.class);
    private final static Map<String, Map<String, WebSocketSession>> activityMemberMap;

    //在线用户
    static {
        activityMemberMap = new ConcurrentHashMap<String, Map<String, WebSocketSession>>();
    }

    /**
     * 关闭连接后
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        System.out.println("afterConnectionClosed: " + JSON.toJSONString(attributes));

        User user = (User) attributes.get("user");
        String userId = user.getId();

        String activityId = (String) attributes.get("activityId");
        Activity activity = activityService.getActivityById(activityId);
        if (activity == null) {
            session.close();
            return;
        }
        Team team = activity.getTeam();
        String teamId = team.getId();
        Member member = memberService.getMemberByUserIdAndTeamId(userId, teamId);
        if (member != null) {
            String memberId = member.getId();
            Map<String, WebSocketSession> memberMap = activityMemberMap.get(teamId);
            if (memberMap.get(memberId) != null) {
                memberMap.remove(memberId);
            }
        }
    }


    /**
     * 连接成功后
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("afterConnectionEstablished: ");
        Map<String, Object> attributes = session.getAttributes();
        System.out.println("afterConnectionEstablished: " + JSON.toJSONString(attributes));

        User user = (User) attributes.get("user");
        String userId = user.getId();

        String activityId = (String) attributes.get("activityId");
        Activity activity = activityService.getActivityById(activityId);

        Team team = activity.getTeam();
        String teamId = team.getId();
        Member member = memberService.getMemberByUserIdAndTeamId(userId, teamId);
        if (member != null) {
            String memberId = member.getId();
            Map<String, WebSocketSession> memberMap = activityMemberMap.get(teamId);
            if (memberMap == null) {
                memberMap = new HashMap<>();
                memberMap.put(member.getId(), session);
                activityMemberMap.put(activityId, memberMap);
            } else {
                if (memberMap.get(memberId) == null) {
                    memberMap.put(member.getId(), session);
                }
            }
        }
    }


    /**
     * 发送信息时， 调用此方法传输数据
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //session.sendMessage(message);
        Map<String, Object> attributes = session.getAttributes();

        User user = (User) attributes.get("user");
        String userId = user.getId();

        String activityId = (String) attributes.get("activityId");
        Activity activity = activityService.getActivityById(activityId);

        Team team = activity.getTeam();
        String teamId = team.getId();
        Member member = memberService.getMemberByUserIdAndTeamId(userId, teamId);
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            JSONObject object = JSONObject
                    .parseObject(message.getPayload().toString());
            Chat chat = new Chat();
            chat.setMember(member);
            chat.setUser(user);
            chat.setActivity(activity);
            chat.setPostTime(object.getString("postTime"));
            chat.setContent(object.getString("content"));
            chat.setBin(binService.getBinById(object.getString("binId")));
            chatService.addChat(chat);
            sendMessageToMembers(activityId, message);
        }
    }


    /**
     * 发送异常时，调用此方法
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable error) throws Exception {
        log.error("WebSocket：handleMessage send message：" + error);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void sendMessageToMember(String activityId, String memberId, WebSocketMessage<?> message) {
        Map<String, WebSocketSession> memberMap = activityMemberMap.get(activityId);
        if (memberMap != null) {
            WebSocketSession session = memberMap.get(memberId);
            if (session != null && session.isOpen()) {
                try {
                    session.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void sendMessageToMembers(String activityId, WebSocketMessage<?> message) {
        Map<String, WebSocketSession> memberMap = activityMemberMap.get(activityId);
        if (memberMap != null) {
            Set<String> memberIds = memberMap.keySet();
            for (String memberId : memberIds) {
                this.sendMessageToMember(activityId, memberId, message);
            }
        }

    }
}