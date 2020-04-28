package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.PartTemplate;
import com.adj.xiaotuanti.service.PartTemplateService;
import com.adj.xiaotuanti.service.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PartTemplateController {
	@Autowired
	private UserService userService;
	@Autowired
	private PartTemplateService partTemplateService;

	private HashMap toMap(PartTemplate partTemplate) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", partTemplate.getId());
		hashMap.put("activityTemplate", partTemplate.getActivityTemplate());
		hashMap.put("order", partTemplate.getOrder());
		hashMap.put("begTime", partTemplate.getBegTime());
		hashMap.put("endTime", partTemplate.getEndTime());
		hashMap.put("content", partTemplate.getContent());
		return hashMap;
	}

	private HashMap[] toMapArray(List<PartTemplate> partTemplateList) {
		HashMap<String, Object>[] hashMaps = new HashMap[partTemplateList.size()];
		for (int index = 0; index < partTemplateList.size(); index++) {
			PartTemplate partTemplate = partTemplateList.get(index);
			hashMaps[index] = toMap(partTemplate);
		}
		return hashMaps;
	}

	@RequestMapping(value = "api/partTemplate/addPartTemplate")
	@ResponseBody // 添加PartTemplate
	public void addPartTemplate(@CookieValue("JSESSIONID") String JSESSIONID, @RequestBody Map<String, Object> map) {
		//User user = userService.getUserByJSESSIONID(JSESSIONID);
		PartTemplate partTemplate = new PartTemplate();
		partTemplateService.addPartTemplate(partTemplate);
	}

	@RequestMapping(value = "api/partTemplate/updatePartTemplate")
	@ResponseBody // 更新PartTemplate
	public void updatePartTemplate(@CookieValue("JSESSIONID") String JSESSIONID, @RequestBody Map<String, Object> map) {
		//User user = userService.getUserByJSESSIONID(JSESSIONID);
		PartTemplate partTemplate = new PartTemplate();
		partTemplateService.updatePartTemplate(partTemplate);
	}

	@RequestMapping(value = "api/partTemplate/deletePartTemplate")
	@ResponseBody // 删除PartTemplate
	public void deletePartTemplate(@CookieValue("JSESSIONID") String JSESSIONID, @RequestBody Map<String, Object> map) {
		//User user = userService.getUserByJSESSIONID(JSESSIONID);
		PartTemplate partTemplate = new PartTemplate();
		partTemplateService.deletePartTemplate(partTemplate);
	}

	@RequestMapping(value = "api/partTemplate/getPartTemplate")
	@ResponseBody // 获取PartTemplate
	public String getPartTemplate(@CookieValue("JSESSIONID") String JSESSIONID, @RequestBody Map<String, Object> map) {
		//User user = userService.getUserByJSESSIONID(JSESSIONID);
		String partTemplateId = map.get("partTemplateId").toString();
		PartTemplate partTemplate = partTemplateService.getPartTemplateById(partTemplateId);
		Map<String, Object> frontResultMap = new HashMap<String, Object>();
		Map data = toMap(partTemplate);
		frontResultMap.put("data", data);
		return JSON.toJSONString(frontResultMap);
	}

}
