package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Freedom;
import com.adj.xiaotuanti.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("FreedomDAO")
public interface FreedomDAO {

    void addFreedom(@Param("freedom") Freedom freedom);

    void updateFreedom(@Param("freedom") Freedom freedom);

    void deleteFreedom(@Param("freedom") Freedom freedom);

    void deleteFreedomById(@Param("freedomId") String freedomId);

    Freedom getFreedom(@Param("freedom") Freedom freedom);

    Freedom getFreedomById(@Param("freedomId") String freedomId);

    List<Freedom> getAllFreedoms();

    Freedom getFreedomByUser(@Param("user") User user);

    List<Freedom> getFreedomsByUser(@Param("user") User user);

    void deleteFreedomByUser(@Param("user") User user);

    void deleteFreedomsByUser(@Param("user") User user);

    Freedom getFreedomByUserId(@Param("userId") String userId);

    void deleteFreedomByUserId(@Param("userId") String userId);

    void deleteFreedomsByUserId(@Param("userId") String userId);

    List<Freedom> getFreedomsByUserId(@Param("userId") String userId);

    List<Freedom> getFreedomsByUserIdAndDay(@Param("userId") String userId, @Param("day") Integer day);

    void deleteFreedomsByUserIdAndDay(@Param("userId") String userId, @Param("day") Integer day);
}
