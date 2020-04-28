package com.adj.xiaotuanti.interceptor;

import com.adj.xiaotuanti.pojo.Token;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.service.UserService;
import com.adj.xiaotuanti.util.RedisUtils;
import com.adj.xiaotuanti.util.ResponseUtils;
import com.adj.xiaotuanti.util.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Component
public class TokenLoginInterceptor implements HandlerInterceptor {

    private final RedisUtils redisUtils;
    private final TokenUtils tokenUtils;
    private final UserService userService;

    @Autowired
    public TokenLoginInterceptor(UserService userService, RedisUtils redisUtils, TokenUtils tokenUtils) {
        this.userService = userService;
        this.redisUtils = redisUtils;
        this.tokenUtils = tokenUtils;
    }

    // 执行Handler方法之前执行
    // 用于身份认证、身份授权
    // 比如身份认证，如果认证通过表示当前用户没有登陆，需要此方法拦截不再向下执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        String userAgent = request.getHeader("user-agent");
        String host = request.getHeader("host");
        Token token = tokenUtils.unSign(authorization);
        if (token != null) {
            User user = userService.getUserByOpenId(token.getOpenId());
            if (user != null
                    && token.getHost().equals(host)
                    && token.getUserAgent().equals(userAgent)
                    && redisUtils.hasKey(user.getId())
                    && authorization.equals(redisUtils.get(user.getId())
            )
            ) {
                System.out.println("user: " + user.toString());
                request.setAttribute("user", user);
                request.setAttribute("openid", token.getOpenId());
                return true;
            }
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("isLogin", false);
        hashMap.put("code", -1);
        hashMap.put("msg", "token 无效，需要重新登录！");
        hashMap.put("user", null);
        ResponseUtils.printMessage(response, hashMap);

        request.setAttribute("user", null);
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex)
            throws Exception {
    }
}
