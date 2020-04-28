package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.NoticeDAO;
import com.adj.xiaotuanti.pojo.Notice;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("NoticeService")
public class NoticeServiceImpl implements NoticeService {

    private final NoticeDAO noticeDAO;

    @Autowired
    public NoticeServiceImpl(NoticeDAO noticeDAO) {
        this.noticeDAO = noticeDAO;
    }

    public void addNotice(Notice notice) {
        noticeDAO.addNotice(notice);
    }

    public void deleteNotice(Notice notice) {
        noticeDAO.deleteNotice(notice);
    }

    public void deleteNoticeById(String NoticeId) {
        noticeDAO.deleteNoticeById(NoticeId);
    }

    public Notice getNoticeById(String NoticeId) {
        return noticeDAO.getNoticeById(NoticeId);
    }

    public List<Notice> getNoticesByUserId(String userId) {
        return noticeDAO.getNoticesByUserId(userId);
    }

    public List<Notice> getNoticesByUser(User user) {
        return noticeDAO.getNoticesByUser(user);
    }

    @Override
    public Integer getNoticesByUserAndIsRead(User user, boolean b) {
        if (b) {
            return noticeDAO.getNoticesByUserAndIsRead(user, 1);
        } else {
            return noticeDAO.getNoticesByUserAndIsRead(user, 0);
        }
    }

    @Override
    public void updateNotice(Notice notice) {
        noticeDAO.updateNotice(notice);
    }
}