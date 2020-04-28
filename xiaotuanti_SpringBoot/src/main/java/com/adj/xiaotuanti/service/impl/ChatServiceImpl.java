
package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.ChatDAO;
import com.adj.xiaotuanti.pojo.Chat;
import com.adj.xiaotuanti.service.ChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("chatService")
public class ChatServiceImpl implements ChatService {

    private final ChatDAO chatDAO;

    @Autowired
    public ChatServiceImpl(ChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }

    public void addChat(Chat chat) {
        chatDAO.addChat(chat);
    }

    public void updateChat(Chat chat) {
        chatDAO.updateChat(chat);
    }

    public void deleteChat(Chat chat) {
        chatDAO.deleteChat(chat);
    }
    
    public void deleteChatById(String chatId){
        chatDAO.deleteChatById(chatId);
    }
    
    public Chat getChat(Chat chat){
        return chatDAO.getChat(chat);
    }
    
    public Chat getChatById(String chatId){
        return chatDAO.getChatById(chatId);
    }

    public List<Chat> getChat100(String activityId){
        return chatDAO.getChat100(activityId);
    }

}
