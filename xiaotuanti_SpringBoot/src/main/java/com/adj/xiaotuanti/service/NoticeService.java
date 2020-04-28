package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Notice;
import com.adj.xiaotuanti.pojo.User;

import java.util.List;

public interface NoticeService {

    void addNotice(Notice notice);

    void deleteNotice(Notice notice);

    void deleteNoticeById(String NoticeId);

    Notice getNoticeById(String NoticeId);

    List<Notice> getNoticesByUserId(String userId);

    List<Notice> getNoticesByUser(User user);

    Integer getNoticesByUserAndIsRead(User user, boolean b);

    void updateNotice(Notice notice);
}