package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.Notice;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class NoticeController {
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    private HashMap<String, Object>[] toMapArray(List<Notice> noticeList) {
        HashMap<String, Object>[] hashMaps = new HashMap[noticeList.size()];
        for (int id = 0; id < noticeList.size(); id++) {
            hashMaps[id] = new HashMap<>();
            Notice notice = noticeList.get(id);
            hashMaps[id].put("id", notice.getId());
            hashMaps[id].put("postTime", notice.getPostTime());
            hashMaps[id].put("content", notice.getContent());
            hashMaps[id].put("isRead", notice.getRead());
        }
        return hashMaps;
    }

    @GetMapping(value = "api/notices/unReadNumber")
    @ResponseBody // 获取信息
    public HashMap<String, Object> getUnReadNoticeNumber(@RequestAttribute("user") User user) {
        Integer unReadNumber = noticeService.getNoticesByUserAndIsRead(user, false);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("code", "1");
        hashMap.put("msg", "获取成功");
        hashMap.put("data", unReadNumber);
        return hashMap;
    }

    @GetMapping(value = "api/notices")
    @ResponseBody // 获取信息
    public HashMap<String, Object> getNotices(@RequestAttribute("user") User user) {
        List<Notice> noticeList = noticeService.getNoticesByUser(user);
        HashMap<String, Object>[] data = toMapArray(noticeList);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("code", "1");
        hashMap.put("msg", "获取成功");
        hashMap.put("data", data);

        for (Notice notice :noticeList){
            notice.setRead(true);
            noticeService.updateNotice(notice);
        }
        return hashMap;
    }

    @PostMapping(value = "api/notices/delete/{noticeId}")
    @ResponseBody // 删除信息：根据NoticeId
    public HashMap<String, Object> deleteNoticeById(@RequestAttribute("user") User user, @PathVariable("noticeId") String noticeId) {
        HashMap<String, Object> result = new HashMap<>();
        Notice notice = noticeService.getNoticeById(noticeId);
        if (notice.getReceiver().getId().equals(user.getId())) {
            noticeService.deleteNoticeById(noticeId);
            result.put("code", "1");
            result.put("msg", "删除成功");
        } else {
            result.put("code", "-1");
            result.put("msg", "无权删除");
        }
        return result;
    }
}
