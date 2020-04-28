package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.Relation_Tag_ActivityTemplate;
import com.adj.xiaotuanti.pojo.Relation_User_ActivityTemplate;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.service.Relation_User_ActivityTemplateService;
import com.adj.xiaotuanti.service.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Relation_User_ActivityTemplateController {
    @Autowired
    private UserService userService;
    @Autowired
    private Relation_User_ActivityTemplateService relationService;

    private HashMap toMap(Relation_User_ActivityTemplate relation) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("user", relation.getUser());
        hashMap.put("activityTemplate", relation.getActivityTemplate());
        return hashMap;
    }

    private HashMap[] toMapArray(List<Relation_User_ActivityTemplate> relationList) {
        HashMap[] hashMaps = new HashMap[relationList.size()];
        for (int index = 0; index < relationList.size(); index++) {
            Relation_User_ActivityTemplate relation = relationList.get(index);
            hashMaps[index] = toMap(relation);
        }
        return hashMaps;
    }

    @GetMapping(value = "api/Relation_User_ActivityTemplates")
    @ResponseBody
    public String getRelation(@RequestAttribute User user, @RequestParam String type, @RequestParam(required = false) Integer templateId) {
        Map<String, Object> frontResultMap = new HashMap<String, Object>();
        frontResultMap.put("data", false);
        if (type.equals("templateId")) {
            Relation_User_ActivityTemplate relation = new Relation_User_ActivityTemplate();
            ActivityTemplate activityTemplate = new ActivityTemplate();
            activityTemplate.setId(templateId);
            relation.setUser(user);
            relation.setActivityTemplate(activityTemplate);
            relation = relationService.getRelation(relation);
            if (relation != null) {
                frontResultMap.put("data", true);
            }
        }
        return JSON.toJSONString(frontResultMap);
    }

    @PostMapping(value = "api/Relation_User_ActivityTemplates")
    @ResponseBody
    public String addRelation(@RequestAttribute User user, @RequestBody Map<String, Object> map) {
        Integer templateId = Integer.parseInt((String) map.get("templateId"));
        Map<String, Object> frontResultMap = new HashMap<String, Object>();
        Relation_User_ActivityTemplate relation = new Relation_User_ActivityTemplate();
        ActivityTemplate activityTemplate = new ActivityTemplate();
        activityTemplate.setId(templateId);
        relation.setUser(user);
        relation.setActivityTemplate(activityTemplate);
        if (relationService.getRelation(relation) != null) {
            frontResultMap.put("code", 1);
            frontResultMap.put("msg", "早已收藏活动模板");
        } else {
            relationService.addRelation(relation);
            frontResultMap.put("code", 1);
            frontResultMap.put("msg", "成功收藏活动模板");
        }

        return JSON.toJSONString(frontResultMap);
    }


    @PostMapping(value = "api/Relation_User_ActivityTemplates/delete/{templateId}")
    @ResponseBody
    public String deleteRelation(@RequestAttribute User user, @PathVariable Integer templateId) {
        Map<String, Object> frontResultMap = new HashMap<String, Object>();
        Relation_User_ActivityTemplate relation = new Relation_User_ActivityTemplate();
        ActivityTemplate activityTemplate = new ActivityTemplate();
        activityTemplate.setId(templateId);
        relation.setUser(user);
        relation.setActivityTemplate(activityTemplate);

        if (relationService.getRelation(relation) != null) {
            relationService.deleteRelation(relation);
        }
        frontResultMap.put("code", 1);
        frontResultMap.put("msg", "成功取消收藏");

        return JSON.toJSONString(frontResultMap);
    }
}
