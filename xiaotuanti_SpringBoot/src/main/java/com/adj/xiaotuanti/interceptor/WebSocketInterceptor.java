package com.adj.xiaotuanti.interceptor;

import com.adj.xiaotuanti.pojo.Token;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.service.UserService;
import com.adj.xiaotuanti.util.RedisUtils;
import com.adj.xiaotuanti.util.ResponseUtils;
import com.adj.xiaotuanti.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

// 登陆拦截器需要检测两个sessionid
// request中的sessionid、Cookie中的sessionid
@Component
public class WebSocketInterceptor extends HttpSessionHandshakeInterceptor {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        // 把获取的请求数据绑定到session的map对象中（attributes）
        HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String authorization = httpServletRequest.getHeader("Authorization");
        String userAgent = httpServletRequest.getHeader("user-agent");
        String host = httpServletRequest.getHeader("host");
        Token token = tokenUtils.unSign(authorization);
        User user = userService.getUserByOpenId(token.getOpenId());
        if (user != null
                && token.getHost().equals(host)
                && redisUtils.hasKey(user.getId())
                && authorization.equals(redisUtils.get(user.getId()))) {
            System.out.println("WebSocket: " + user.toString() + " 已经授权，用户已登陆！");
            attributes.put("activityId", httpServletRequest.getParameter("activityId"));
            attributes.put("openId", token.getOpenId());
            attributes.put("user", user);
           return true;
//            return super.beforeHandshake(request, response, wsHandler, attributes);
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}

