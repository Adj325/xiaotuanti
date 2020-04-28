package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.FreedomDAO;
import com.adj.xiaotuanti.dao.TeamDAO;
import com.adj.xiaotuanti.dao.UserDAO;
import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Freedom;
import com.adj.xiaotuanti.pojo.Team;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final TeamDAO teamDAO;
    private final FreedomDAO freedomDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, TeamDAO teamDAO, FreedomDAO freedomDAO) {
        this.userDAO = userDAO;
        this.teamDAO = teamDAO;
        this.freedomDAO = freedomDAO;
    }

    // 添加用户，sessionid不能为空
    public void addUser(User user) {
        User tempUser = getUser(user);
        if (tempUser == null) {
            System.out.println("\n不存在，选择添加！\n");
            userDAO.addUser(user);

            // 创建一个个人团体
            Team team = new Team();
            team.setUserOwner(user);
            teamDAO.addTeam(team);

            //  创建默认空闲时间
            for (int day=1; day<=7; day++){
                Freedom freedom = new Freedom();
                freedom.setUserOwner(user);
                freedom.setDay(day);
                freedom.setBegTime(60*6);
                freedom.setEndTime(60*22);
                freedomDAO.addFreedom(freedom);
            }
        } else {
            System.out.println("\n已存在，选择更新！");
            System.out.println("数据库：" + tempUser.toString() + "\n");
            user.setId(tempUser.getId());
            userDAO.updateUser(user);
        }
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public User getUserById(String userId) {
        return userDAO.getUserById(userId);
    }

    public User getUser(User user) {
        //System.out.println("\n用户：" + user.toString() + "\n");
        return userDAO.getUser(user);
    }

    public User getUserByMemberId(String memberId) {
        return userDAO.getUserByMemberId(memberId);
    }

    public User getUserByJSESSIONID(String JSESSIONID) {
        User user = new User();
        user.setSessionid(JSESSIONID);
        return getUser(user);
    }

    public User getUserByOpenId(String openId){
        User user = new User();
        user.setOpenid(openId);
        return getUser(user);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public List<User> getUsersByTeamId(String teamId) {
        return userDAO.getUsersByTeamId(teamId);
    }

    public List<User> getCandidatesByActivity(Activity activity) {
        return userDAO.getCandidatesByActivity(activity);
    }

    public List<User> getWillingUsersByActivityId(String activityId) {
        return userDAO.getWillingUsersByActivityId(activityId);
    }

    public List<User> getWillingUsersByActivity(Activity activity) {
        return userDAO.getWillingUsersByActivityId(activity.getId());
    }

    @Override
    public List<User> getWillingUsersByActivityIdAndWillingType(String activityId, int i) {
        return userDAO.getWillingUsersByActivityIdAndWillingType(activityId, i);
    }
}
