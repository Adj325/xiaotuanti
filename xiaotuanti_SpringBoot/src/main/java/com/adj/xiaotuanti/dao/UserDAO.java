package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserDAO")
public interface UserDAO {

    void addUser(@Param("user") User user);

    void updateUser(@Param("user") User user);

    User getUserByMemberId(@Param("memberId") String memberId);

    User getUserById(@Param("userId") String userid);

    User getUser(@Param("user") User user);

    List<User> getAllUsers();

    List<User> getUsersByTeamId(@Param("teamId") String teamId);

    List<User> getCandidatesByActivity(@Param("activity") Activity activity);

    List<User> getWillingUsersByActivityId(@Param("activityId") String activityId);

    List<User> getWillingUsersByActivityIdAndWillingType(@Param("activityId") String activityId, @Param("willingType") Integer willingType);
}
