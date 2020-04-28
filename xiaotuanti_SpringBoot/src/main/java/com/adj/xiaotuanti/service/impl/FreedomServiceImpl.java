package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.FreedomDAO;
import com.adj.xiaotuanti.pojo.Freedom;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.pojo.vo.FreedomVo;
import com.adj.xiaotuanti.service.FreedomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("freedomService")
public class FreedomServiceImpl implements FreedomService {

    @Autowired
    private FreedomDAO freedomDAO;

    public void addFreedom(Freedom freedom) {
        freedomDAO.addFreedom(freedom);
    }

    public void updateFreedom(Freedom freedom) {
        freedomDAO.updateFreedom(freedom);
    }

    public void deleteFreedom(Freedom freedom) {
        freedomDAO.deleteFreedom(freedom);
    }

    public void deleteFreedomById(String freedomId) {
        freedomDAO.deleteFreedomById(freedomId);
    }

    public Freedom getFreedom(Freedom freedom) {
        return freedomDAO.getFreedom(freedom);
    }

    public Freedom getFreedomById(String freedomId) {
        return freedomDAO.getFreedomById(freedomId);
    }

    public List<Freedom> getAllFreedoms() {
        return freedomDAO.getAllFreedoms();
    }

    public Freedom getFreedomByUser(User user) {
        return freedomDAO.getFreedomByUser(user);
    }

    public List<Freedom> getFreedomsByUser(User user) {
        return freedomDAO.getFreedomsByUser(user);
    }

    public void deleteFreedomsByUser(User user) {
        freedomDAO.deleteFreedomsByUser(user);
    }

    public void deleteFreedomsByUserId(String userId) {
        freedomDAO.deleteFreedomsByUserId(userId);
    }

    @Override
    public List<Freedom> getFreedomsByUserIdAndDay(String userId, Integer dayId) {
        return freedomDAO.getFreedomsByUserIdAndDay(userId, dayId);
    }

    @Override
    public void deleteFreedomsByUserIdAndDay(String userId, Integer day) {
        freedomDAO.deleteFreedomsByUserIdAndDay(userId, day);

    }

    @Override
    public FreedomVo[][] constructForUser(User user) {
        FreedomVo[][] freedoms = new FreedomVo[7][];
        for (int dayId = 1; dayId <= 7; dayId++) {
            List<Freedom> freedomList = this.getFreedomsByUserIdAndDay(user.getId(), dayId);
            freedoms[dayId - 1] = new FreedomVo[freedomList.size()];
            for (int i = 0; i < freedomList.size(); i++) {
                freedoms[dayId - 1][i] = new FreedomVo(freedomList.get(i));
            }
        }
        return freedoms;
    }

    public List<Freedom> getFreedomsByUserId(String userId) {
        return freedomDAO.getFreedomsByUserId(userId);
    }

}