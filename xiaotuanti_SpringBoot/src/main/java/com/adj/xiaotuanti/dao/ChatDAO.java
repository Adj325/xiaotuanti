
package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Chat;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ChatDAO")
public interface ChatDAO {

    void addChat(@Param("chat") Chat chat);

    void updateChat(@Param("chat") Chat chat);

    void deleteChat(@Param("chat") Chat chat);

    void deleteChatById(@Param("chatId") String chatId);

    Chat getChat(@Param("chat") Chat chat);

    Chat getChatById(@Param("chatId") String chatId);

    List<Chat> getChat100(@Param("activityId") String activityId);

}
