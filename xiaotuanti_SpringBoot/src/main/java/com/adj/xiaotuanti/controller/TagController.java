package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.Tag;
import com.adj.xiaotuanti.service.TagService;
import com.adj.xiaotuanti.service.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TagController {
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;

    private HashMap toMap(Tag tag) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id", tag.getId());
        hashMap.put("name", tag.getName());
        return hashMap;
    }

    private HashMap[] toMapArray(List<Tag> tagList) {
        HashMap[] hashMaps = new HashMap[tagList.size()];
        for (int index = 0; index < tagList.size(); index++) {
            Tag tag = tagList.get(index);
            hashMaps[index] = new HashMap();
            hashMaps[index].put("id", tag.getId());
            hashMaps[index].put("name", tag.getName());
        }
        return hashMaps;
    }

    @RequestMapping(value = "api/tag/addTag")
    @ResponseBody // 添加Tag
    public void addTag(@CookieValue("JSESSIONID") String JSESSIONID, @RequestBody Map<String, Object> map) {
        //User user = userService.getUserByJSESSIONID(JSESSIONID);
        Tag tag = new Tag(map);
        tagService.addTag(tag);
    }

    @RequestMapping(value = "api/tag/updateTag")
    @ResponseBody // 更新Tag
    public void updateTag(@CookieValue("JSESSIONID") String JSESSIONID, @RequestBody Map<String, Object> map) {
        //User user = userService.getUserByJSESSIONID(JSESSIONID);
        Tag tag = new Tag(map);
        tagService.updateTag(tag);
    }

    @RequestMapping(value = "api/tag/deleteTag")
    @ResponseBody // 删除Tag
    public void deleteTag(@CookieValue("JSESSIONID") String JSESSIONID, @RequestBody Map<String, Object> map) {
        //User user = userService.getUserByJSESSIONID(JSESSIONID);
        Tag tag = new Tag(map);
        tagService.deleteTag(tag);
    }

    @RequestMapping(value = "api/tag/getTag")
    @ResponseBody // 获取Tag
    public String getTag(@CookieValue("JSESSIONID") String JSESSIONID, @RequestBody Map<String, Object> map) {
        //User user = userService.getUserByJSESSIONID(JSESSIONID);
        String tagId = map.get("tagId").toString();
        Tag tag = tagService.getTagById(tagId);
        Map<String, Object> frontResultMap = new HashMap<String, Object>();
        Map data = toMap(tag);
        frontResultMap.put("data", data);
        return JSON.toJSONString(frontResultMap);
    }

}
