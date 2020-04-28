package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.dao.Relation_User_ActivityTemplateDAO;
import com.adj.xiaotuanti.pojo.*;
import com.adj.xiaotuanti.pojo.constants.MemberLevel;
import com.adj.xiaotuanti.pojo.dto.ActivityTemplateDto;
import com.adj.xiaotuanti.service.*;
import com.adj.xiaotuanti.util.WordUtils;
import com.alibaba.fastjson.JSON;
import org.nlpcn.commons.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ActivityTemplateController {
    private final TagService tagService;
    private final MemberService memberService;
    private final ActivityService activityService;
    private final PartTemplateService partTemplateService;
    private final ActivityTemplateService activityTemplateService;
    private final Relation_Tag_ActivityTemplateService relationTagActivityTemplateService;

    @Autowired
    public ActivityTemplateController(TagService tagService, MemberService memberService, ActivityService activityService, PartTemplateService partTemplateService, ActivityTemplateService activityTemplateService, Relation_Tag_ActivityTemplateService relationTagActivityTemplateService) {
        this.tagService = tagService;
        this.memberService = memberService;
        this.activityService = activityService;
        this.partTemplateService = partTemplateService;
        this.activityTemplateService = activityTemplateService;
        this.relationTagActivityTemplateService = relationTagActivityTemplateService;
    }

    private HashMap<String, Object> toMap(ActivityTemplate activityTemplate) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id", activityTemplate.getId());
        if (activityTemplate.getTeamOwner() != null) {
            hashMap.put("teamId", activityTemplate.getTeamOwner().getId());
            hashMap.put("teamName", activityTemplate.getTeamOwner().getName());
        }
        hashMap.put("name", activityTemplate.getName());
        hashMap.put("introduction", activityTemplate.getIntroduction());
        hashMap.put("open", activityTemplate.getOpen());
        hashMap.put("tags", activityTemplate.getTags());
        hashMap.put("parts", activityTemplate.getParts());
        hashMap.put("collectNumber", activityTemplate.getCollectNumber());
        return hashMap;
    }

    private HashMap<String, Object>[] toMapArray(List<ActivityTemplate> activityTemplateList) {
        HashMap<String, Object>[] hashMaps = new HashMap[activityTemplateList.size()];
        for (int index = 0; index < activityTemplateList.size(); index++) {
            ActivityTemplate activityTemplate = activityTemplateList.get(index);
            hashMaps[index] = toMap(activityTemplate);
        }
        return hashMaps;
    }

    @GetMapping(value = "api/templates/{templateId}")
    public HashMap<String, Object> getActivityTemplate(@RequestAttribute User user, @PathVariable Integer templateId) {
        HashMap<String, Object> frontResultMap = new HashMap<String, Object>();
        ActivityTemplate template = activityTemplateService.getActivityTemplateById(templateId);
        Team team = template.getTeamOwner();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), team.getId());
        if (member == null) {
            frontResultMap.put("permission", -1);
        } else {
            frontResultMap.put("permission", member.getLevel().ordinal());
        }
        frontResultMap.put("data", toMap(template));
        return frontResultMap;
    }

    @GetMapping(value = "api/templates")
    public HashMap<String, Object> getActivityTemplate(@RequestAttribute User user,
                                                       @RequestParam(required = true) String type,
                                                       @RequestParam(required = false) String tagList,
                                                       @RequestParam(required = false) String teamId,
                                                       @RequestParam(required = false) String keyword) {
        List<ActivityTemplate> templates = new ArrayList<>();
        HashMap<String, Object> frontResultMap = new HashMap<String, Object>();
        switch (type) {
            case "all":
                if (StringUtil.isNotBlank(keyword)) {
                    List<String> keywords = WordUtils.getNlpSeg(keyword);
                    templates = activityTemplateService.getActivityTemplatesByOpenAndTeamNotOpenAndKeywords(user.getId(), keywords);
                } else {
                    templates = activityTemplateService.getActivityTemplatesByOpenAndTeamNotOpen(user.getId());
                }
                break;
            case "user":
                templates = activityTemplateService.getActivityTemplatesByUserId(user.getId());
                break;
            case "team":
                templates = activityTemplateService.getActivityTemplatesByTeamId(teamId);
                break;
            case "userIdOrTeamId":
                if (StringUtil.isBlank(tagList)){
                    templates = activityTemplateService.getActivityTemplatesByUserIdOrTeamId(user.getId(), teamId);
                }else {
                    String[] tagNames = tagList.split("#");
                    templates = activityTemplateService.getActivityTemplatesByUserIdOrTeamIdOrTagNames(user.getId(), teamId, tagNames);
                }

                break;
            default:
        }
        frontResultMap.put("data", toMapArray(templates));
        return frontResultMap;
    }

    @PostMapping(value = "api/templates")
    public HashMap<String, Object> addActivityTemplate(
            @RequestAttribute User user, @RequestBody ActivityTemplateDto dto) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        String activityId = dto.getActivityId();
        if (StringUtil.isBlank(activityId)) {
            result.put("code", -1);
            result.put("msg", "活动不存在");
            return result;
        }
        Activity activity = activityService.getActivityById(activityId);
        if (activity == null) {
            result.put("code", -1);
            result.put("msg", "活动不存在");
            return result;
        }
        Team team = activity.getTeam();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), team.getId());
        if (member != null && member.getLevel().ordinal() >= 1) {
            ActivityTemplate template = new ActivityTemplate(activity);
            template.setCreateTime(new Date());
            activityTemplateService.addActivityTemplate(template);
            for (Part part : activity.getParts()) {
                PartTemplate partTemplate = new PartTemplate(part);
                partTemplate.setActivityTemplate(template);
                partTemplateService.addPartTemplate(partTemplate);
            }
            for (Tag tag : template.getTags()) {
                relationTagActivityTemplateService.addRelation(new Relation_Tag_ActivityTemplate(tag, template));
            }
            result.put("code", 1);
            result.put("msg", "成功保存为模板");
            return result;
        } else {
            result.put("code", -1);
            result.put("msg", "无权操作");
            return result;
        }
    }

    @PutMapping(value = "api/templates/{templateId}")
    public HashMap updateActivityTemplate(
            @RequestAttribute User user, @PathVariable Integer templateId, @RequestBody ActivityTemplateDto activityTemplateDto) {

        HashMap<String, Object> result = new HashMap<String, Object>();
        ActivityTemplate template = activityTemplateService.getActivityTemplateById(templateId);
        if (template == null) {
            result.put("code", -1);
            result.put("msg", "模板不存在");
            return result;
        }
        Team team = template.getTeamOwner();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), team.getId());
        if (member != null && member.getLevel().ordinal() >= 1) {
            ActivityTemplate activityTemplate = new ActivityTemplate(activityTemplateDto);
            activityTemplate.setId(templateId);
            activityTemplate.setTeamOwner(team);
            activityTemplateService.updateActivityTemplate(activityTemplate);
            // 删除 旧阶段
            partTemplateService.deletePartTemplatesByActivityTemplate(template);
            // 添加 新阶段
            int order = 0;
            for (PartTemplate partTemplate : activityTemplateDto.getParts()) {
                partTemplate.setOrder(order++);
                partTemplate.setActivityTemplate(template);
                partTemplateService.addPartTemplate(partTemplate);
            }
            // 删除旧联系
            relationTagActivityTemplateService.deleteRelationsByActivityTemplate(template);
            // 添加新联系
            for (String tagName : activityTemplateDto.getTagString().split("#")) {
                Tag tag = tagService.getTagByName(tagName);
                if (tag == null) {
                    tag = new Tag(tagName);
                    tagService.addTag(tag);
                }
                relationTagActivityTemplateService.addRelation(new Relation_Tag_ActivityTemplate(tag, template));
            }
            result.put("code", 1);
            result.put("msg", "更新成功");
            return result;
        } else {
            result.put("code", -1);
            result.put("msg", "无权操作");
            return result;
        }
    }

    @PostMapping(value = "api/templates/delete/{templateId}")
    public HashMap deleteActivityTemplate(
            @RequestAttribute User user, @PathVariable Integer templateId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        ActivityTemplate activityTemplate = activityTemplateService.getActivityTemplateById(templateId);
        if (activityTemplate == null) {
            result.put("code", -1);
            result.put("msg", "模板不存在");
            return result;
        }
        Team team = activityTemplate.getTeamOwner();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), team.getId());
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            activityTemplateService.deleteActivityTemplateById(templateId);
            result.put("code", 1);
            result.put("msg", "删除成功");
            return result;
        } else {
            result.put("code", -1);
            result.put("msg", "无权操作");
            return result;
        }
    }
}
