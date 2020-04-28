package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.*;
import com.adj.xiaotuanti.service.CommentService;
import com.adj.xiaotuanti.service.NoticeService;
import com.adj.xiaotuanti.service.UserService;
import com.adj.xiaotuanti.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Value("${xiaotuanti.wechat.appId}")
    private String appId;
    @Value("${xiaotuanti.wechat.secretId}")
    private String secretId;

    private final RedisUtils redisUtils;
    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final NoticeService noticeService;
    private final CommentService commentService;

    @Autowired
    public UserController(RedisUtils redisUtils, TokenUtils tokenUtils, UserService userService, CommentService commentService, NoticeService noticeService) {
        this.redisUtils = redisUtils;
        this.tokenUtils = tokenUtils;
        this.userService = userService;
        this.commentService = commentService;
        this.noticeService = noticeService;
    }

    // 微信用户登陆
    @PostMapping(value = "api/users/login")
    public HashMap<String, Object> login(HttpServletRequest request, @RequestBody HashMap<String, Object> map) {
        String userAgent = request.getHeader("user-agent");
        String host = request.getHeader("host");
        String code = (String) map.get("code");
        // 返回给前端的结果
        HashMap<String, Object> result = new HashMap<>();

        System.out.printf("\n本次登录中，code=%s\n", code);

        // 根据appid及secret构造验证url
        String validateUrl = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                appId, secretId, code);

        // 访问腾讯验证api，获取session_key及openid
        String responseStr;
        try {
            responseStr = HTTPUtils.doGet(validateUrl);
        } catch (NullPointerException e) {
            result.put("code", "-1");
            result.put("msg", "code 无效");
            return result;
        }
        Map responseMap = (Map) JSON.parse(responseStr);
        System.out.println("后端登陆结果：" + JSON.toJSONString(responseMap));

        if (responseMap == null || !responseMap.containsKey("openid")) {
            result.put("code", "-1");
            result.put("msg", "code 无效");
            return result;
        }

        // 添加用户到数据库
        User user = new User();
        user.setOpenid(responseMap.get("openid").toString());
        user.setSessionkey(responseMap.get("session_key").toString());

        // 有则添加，无则更新
        userService.addUser(user);

        // 获取数据库中存储的用户信息
        user = userService.getUser(user);

        // 过期时间为60分钟

        Token token = new Token(user.getOpenid(), host, userAgent);
        String tokenString = tokenUtils.sign(token, 1000L * 60L * 60L);
        // 封装成对象返回给客户端
        result.put("openid", user.getOpenid());
        result.put("authorization", tokenString);
        result.put("user", user);

        // 6小时后，过期
        redisUtils.set(user.getId(), tokenString, 60 * 60 * 6);
        return result;
    }

    // 测试用户是否登陆
    @RequestMapping(value = "api/users/isLogin")
    public HashMap<String, Object> isLogin(HttpServletRequest httpServletRequest) {
        String tokenAuthorization = httpServletRequest.getHeader("Authorization");
        System.out.println("Authorization: " + tokenAuthorization);
        Token token = tokenUtils.unSign(tokenAuthorization);
        HashMap<String, Object> result = new HashMap<>();
        if (token == null) {
            System.out.println("token 无效");
            result.put("isLogin", false);
            result.put("code", -1);
            result.put("msg", "token 无效");
            result.put("user", null);
            return result;
        }
        System.out.println("openidInToken: " + token.getOpenId());
        User user = userService.getUserByOpenId(token.getOpenId());


        if (user != null
                && redisUtils.hasKey(user.getId())
                && token.equals(redisUtils.get(user.getId()))
        ) {
            System.out.println("user: " + user.toString());
            result.put("isLogin", true);
            result.put("code", 1);
            result.put("msg", "已登录！");
            result.put("user", user);
        } else {
            System.out.println("token 无效");
            result.put("isLogin", false);
            result.put("code", -1);
            result.put("msg", "token 无效");
            result.put("user", null);
        }

        return result;
    }

    // 获取用户
    @GetMapping(value = "api/users/me")
    @ResponseBody
    public HashMap<String, Object> getUser(@RequestAttribute("user") User user) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("code", "1");
        hashMap.put("data", user);
        hashMap.put("msg", "获取用户成功");
        return hashMap;
    }

    // 更新用户
    @PutMapping(value = "api/users")
    @ResponseBody // 上传加密数据
    public HashMap<String, Object> updateUser(
            @RequestAttribute User user,
            @RequestBody HashMap<String, Object> map) {
        System.out.println("更新用户: " + JSON.toJSONString(map));
        String type = (String) map.get("type");
        System.out.println("updateUser: " + type);
        if (type == null) {
            type = "plain";
        }
        switch (type) {
            case "plain":
                return updateUserByPlainData(user, map);
            case "Encrypted":
                return updateUserByEncryptedData(user, map);
            default:
                HashMap<String, Object> result = new HashMap<>();
                result.put("code", "-1");
                result.put("msg", "type 参数无效");
                return result;
        }

    }

    // 上传加密数据
    private HashMap<String, Object> updateUserByEncryptedData(User user, HashMap map) {
        HashMap<String, Object> result = new HashMap<>();
        // 根据JSESSIONID获取用户

        // JSESSIONID未授权
        if (user == null) {
            result.put("code", -1);
            result.put("msg", "非法访问");
            return result;
        }

        // 经sessionkey加密后的用户数据
        String encryptedData = map.get("encryptedData").toString();
        String iv = map.get("iv").toString();

        // 使用key解密数据
        JSONObject jsonObject = WXBizDataCrypt.getUserInfo(encryptedData, user.getSessionkey(), iv);
        if (jsonObject == null) {
            result.put("code", 1);
            result.put("msg", "数据有误");
        } else {

            // 设置信息
            user.setAvatarUrl(jsonObject.getString("avatarUrl"));
            user.setCity(jsonObject.getString("city"));
            user.setCountry(jsonObject.getString("country"));
            user.setProvince(jsonObject.getString("province"));
            user.setGender(Integer.getInteger(jsonObject.getString("gender")));
            user.setNickName(jsonObject.getString("nickName"));

            // 更新用户
            userService.updateUser(user);
            result.put("code", 0);
            result.put("msg", "上传成功，更新成功！");

        }
        return result;
    }

    // 修改用户
    private HashMap<String, Object> updateUserByPlainData(User oldUser, HashMap map) {
        HashMap<String, Object> result = new HashMap<>();
        // 根据JSESSIONID获取用户
        User newUser = new User(map);
        // 设置信息-用于myabtis更新
        newUser.setOpenid(oldUser.getOpenid());
        newUser.setId(oldUser.getId());
        userService.updateUser(newUser);
        result.put("code", "1");
        result.put("msg", "更新用户信息");
        return result;
    }
}
