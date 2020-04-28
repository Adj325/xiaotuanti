package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Notice;
import com.adj.xiaotuanti.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("NoticeDAO")
public interface NoticeDAO {

    void addNotice(@Param("notice") Notice notice);

    void deleteNotice(@Param("notice") Notice notice);

    void deleteNoticeById(@Param("noticeId") String noticeId);

    Notice getNoticeById(@Param("noticeId") String noticeId);

    List<Notice> getNoticesByUserId(@Param("userId") String userId);

    List<Notice> getNoticesByUser(@Param("user") User user);

    Integer getNoticesByUserAndIsRead(@Param("user") User user, @Param("isRead") Integer isRead);

    void updateNotice(@Param("notice") Notice notice);
}
