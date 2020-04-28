package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.Item;
import com.adj.xiaotuanti.pojo.Member;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.pojo.constants.MemberLevel;
import com.adj.xiaotuanti.pojo.dto.ItemDto;
import com.adj.xiaotuanti.service.ItemService;
import com.adj.xiaotuanti.service.MemberService;
import com.adj.xiaotuanti.service.TeamService;
import org.nlpcn.commons.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ItemController {

    private final ItemService itemService;
    private final MemberService memberService;
    private final TeamService teamService;

    @Autowired
    public ItemController(ItemService itemService, MemberService memberService, TeamService teamService) {
        this.itemService = itemService;
        this.memberService = memberService;
        this.teamService = teamService;
    }

    private HashMap<String, Object> toMap(Item item) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", item.getId());
        hashMap.put("teamId", item.getTeam().getId());
        hashMap.put("teamName", item.getTeam().getName());
        hashMap.put("contact", item.getContact());
        hashMap.put("introduction", item.getIntroduction());
        return hashMap;
    }

    private HashMap<String, Object>[] toMapArray(List<Item> itemList) {
        HashMap<String, Object>[] hashMaps = new HashMap[itemList.size()];
        for (int index = 0; index < itemList.size(); index++) {
            Item item = itemList.get(index);
            hashMaps[index] = toMap(item);
        }
        return hashMaps;
    }

    @PostMapping(value = "api/items")
    @ResponseBody // 添加Item
    public HashMap addItem(@RequestAttribute User user, @RequestBody HashMap<String, Object> map) {
        HashMap<String, Object> result = new HashMap<>();
        String teamId = map.get("teamId").toString();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            Item item = new Item(map);
            item.setTeam(teamService.getTeamById(teamId));
            itemService.addItem(item);
            result.put("code", 0);
            result.put("msg", "添加成功");
        } else {
            result.put("code", -1);
            result.put("msg", "无权添加");
        }
        return result;
    }

    @PutMapping(value = "api/items/{itemId}")
    @ResponseBody // 更新Item
    public HashMap updateItem(
            @RequestAttribute User user,
            @PathVariable("itemId") String itemId,
            @RequestBody ItemDto itemDto) {
        HashMap<String, Object> result = new HashMap<>();
        Item item = itemService.getItemById(itemId);
        if (item == null) {
            result.put("code", -1);
            result.put("msg", "非本团物资");
            return result;
        }
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), item.getTeam().getId());
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            item.setContact(itemDto.getContact());
            item.setIntroduction(itemDto.getIntroduction());
            itemService.updateItem(item);
            result.put("code", 1);
            result.put("msg", "更新成功");
        } else {
            result.put("code", -1);
            result.put("msg", "无权更新");
        }
        return result;
    }

    @PostMapping(value = "api/items/delete")
    @ResponseBody // 删除Item
    public HashMap deleteItem(
            @RequestAttribute User user,
            @RequestBody ItemDto itemDto) {
        String itemId = itemDto.getItemId();
        HashMap<String, Object> result = new HashMap<>();
        Item item = itemService.getItemById(itemId);
        if (item == null) {
            result.put("code", -1);
            result.put("msg", "非本团物资");
            return result;
        }
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), item.getTeam().getId());
        if (member != null && member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal()) {
            itemService.deleteItemById(itemId);
            result.put("code", 1);
            result.put("msg", "删除成功");
        } else {
            result.put("code", -1);
            result.put("msg", "无权删除");
        }
        return result;
    }

    @GetMapping(value = "api/items")
    @ResponseBody // 获取Item
    public HashMap getItems(
            @RequestAttribute User user,
            @RequestParam String type,
            @RequestParam(value = "teamId", required = false) String teamId,
            @RequestParam(value = "keyword", required = false) String keyword) {
        switch (type) {
            case "all":
                if (StringUtil.isNotBlank(keyword)) {
                    return getItemsByKeyword(keyword);
                } else {
                    return getAllItems();
                }
            case "teamId":
                return getItemsByTeamId(user, teamId);
            case "keyword":
                return getItemsByKeyword(keyword);
            case "teamIdAndKeyword":
                return getItemsByTeamIdAndKeyword(user, teamId, keyword);
            default:
                HashMap<String, Object> result = new HashMap<>();
                result.put("code", "-1");
                result.put("msg", "type 无效");
                return result;
        }
    }

    // 获取Item TeamId
    private HashMap getItemsByTeamId(
            @RequestAttribute User user,
            @PathVariable String teamId) {
        HashMap<String, Object> result = new HashMap<>();
        List<Item> itemList = itemService.getItemsByTeamId(teamId);
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        if (member != null) {
            result.put("permission", member.getLevel().ordinal());
        } else {
            result.put("permission", -1);
        }
        result.put("data", toMapArray(itemList));
        result.put("msg", "获取成功");
        return result;
    }

    // 搜索Item TeamId
    private HashMap getItemsByTeamIdAndKeyword(User user, String teamId, String keyword) {
        HashMap<String, Object> result = new HashMap<>();
        List<Item> itemList = itemService.getItemsByKeyword(keyword);
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        if (member != null) {
            result.put("permission", member.getLevel().ordinal());
        } else {
            result.put("permission", -1);
        }
        result.put("data", toMapArray(itemList));
        result.put("msg", "获取成功");
        return result;
    }

    // 搜索Item TeamId
    private HashMap getItemsByKeyword(String keyword) {
        HashMap<String, Object> result = new HashMap<>();
        List<Item> itemList = itemService.getItemsByKeyword(keyword);
        result.put("data", toMapArray(itemList));
        result.put("msg", "获取成功");
        return result;
    }

    // 获取所有Item
    private HashMap getAllItems() {
        HashMap<String, Object> result = new HashMap<>();
        List<Item> itemList = itemService.getAllItems();
        Map[] data = toMapArray(itemList);
        result.put("code", 1);
        result.put("msg", "获取成功");
        result.put("data", data);
        return result;
    }
}
