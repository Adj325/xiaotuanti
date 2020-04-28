
package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Chat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatService {

    void addChat(Chat chat);
    
    void updateChat(Chat chat);
    
    void deleteChat(Chat chat);
    
    void deleteChatById(String chatId);
    
    Chat getChat(Chat chat);
    
    Chat getChatById(String chatId);

    List<Chat> getChat100(String activityId);

}
    