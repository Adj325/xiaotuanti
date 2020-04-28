package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Freedom;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.pojo.vo.FreedomVo;

import java.util.List;

public interface FreedomService {

    void addFreedom(Freedom freedom);

    void updateFreedom(Freedom freedom);

    void deleteFreedom(Freedom freedom);

    void deleteFreedomById(String freedomId);

    Freedom getFreedom(Freedom freedom);

    Freedom getFreedomById(String freedomId);

    List<Freedom> getAllFreedoms();

    Freedom getFreedomByUser(User user);

    List<Freedom> getFreedomsByUser(User user);

    void deleteFreedomsByUserId(String userId);

    void deleteFreedomsByUser(User user);

    List<Freedom> getFreedomsByUserId(String userId);

    List<Freedom> getFreedomsByUserIdAndDay(String id, Integer dayId);

    void deleteFreedomsByUserIdAndDay(String id, Integer day);

    FreedomVo[][] constructForUser(User user);
}