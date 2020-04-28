package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    // 根据openid更新
    void updateUser(User user);

    // 添加用户，sessionid不能为空
    void addUser(User user);

    User getUser(User user);

    User getUserById(String userId);

    User getUserByMemberId(String memberId);

    User getUserByJSESSIONID(String JSESSIONID);

    User getUserByOpenId(String openId);

    List<User> getAllUsers();

    List<User> getUsersByTeamId(String teamId);

    List<User> getCandidatesByActivity(Activity activity);

    List<User> getWillingUsersByActivityId(String activityId);

    List<User> getWillingUsersByActivity(Activity activity);

    List<User> getWillingUsersByActivityIdAndWillingType(String activityId, int i);
}
