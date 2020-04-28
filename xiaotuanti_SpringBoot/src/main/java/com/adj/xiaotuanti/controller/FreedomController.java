package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.Freedom;
import com.adj.xiaotuanti.pojo.Member;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.pojo.dto.FreedomDto;
import com.adj.xiaotuanti.pojo.vo.UserVo;
import com.adj.xiaotuanti.service.FreedomService;
import com.adj.xiaotuanti.service.MemberService;
import com.adj.xiaotuanti.service.UserService;
import com.adj.xiaotuanti.util.ExcelUtils;
import com.adj.xiaotuanti.util.FreeTimeUtils;
import com.adj.xiaotuanti.util.RSAUtils;
import com.adj.xiaotuanti.util.ScheduleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FreedomController {
    @Autowired
    private UserService userService;
    @Autowired
    private FreedomService freedomService;
    @Autowired
    private MemberService memberService;

    private HashMap toMap(Freedom freedom) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id", freedom.getId());
        hashMap.put("day", freedom.getDay());
        hashMap.put("begTime", FreeTimeUtils.m2h(freedom.getBegTime()));
        hashMap.put("endTime", FreeTimeUtils.m2h(freedom.getEndTime()));
        return hashMap;
    }

    private HashMap[] toMapArray(List<Freedom> freedomList) {
        HashMap<String, Object>[] hashMaps = new HashMap[freedomList.size()];
        for (int index = 0; index < freedomList.size(); index++) {
            Freedom freedom = freedomList.get(index);
            hashMaps[index] = new HashMap<String, Object>();
            hashMaps[index].put("id", freedom.getId());
            hashMaps[index].put("day", freedom.getDay());
            hashMaps[index].put("begTime", FreeTimeUtils.m2h(freedom.getBegTime()));
            hashMaps[index].put("endTime", FreeTimeUtils.m2h(freedom.getEndTime()));
        }
        return hashMaps;
    }

    @GetMapping(value = "api/freedom")
    @ResponseBody
    public HashMap<String, Object> getFreedom(@RequestAttribute User user, String type, String teamId) {

        switch (type) {
            case "user":
                return getFreedomByUser(user);
            case "teamId":
                return getFreedomByTeamId(user, teamId);
            default:
                HashMap<String, Object> result = new HashMap<>();
                result.put("code", -1);
                result.put("msg", "type 有误");
                return result;

        }
    }

    private HashMap<String, Object> getFreedomByUser(User user) {
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object>[][] data = new HashMap[7][];
        Freedom[][] freedoms = freedomService.constructForUser(user);
        result.put("code", 1);
        result.put("msg", "获取成功");
        result.put("data", freedoms);
        return result;
    }

    private HashMap<String, Object> getFreedomByTeamId(User user, String teamId) {
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        if (member == null) {
            result.put("permission", -1);
            result.put("msg", "无权获取");
            result.put("data", null);
            return result;
        }

        result.put("permission", member.getLevel().ordinal());
        List<User> teamMemberUsers = userService.getUsersByTeamId(teamId);
        List<UserVo> userVos = new ArrayList<>();
        HashMap<String, Object> id_name_dict = new HashMap<>();
        // 构造 id：name，统计有课表状态
        for (User teamMemberUser : teamMemberUsers) {
            id_name_dict.put(teamMemberUser.getId() + "", teamMemberUser.getRealName());
            UserVo userVo = new UserVo();
            userVo.setId(user.getId());
            userVo.setNickName(teamMemberUser.getNickName());
            userVo.setRealName(teamMemberUser.getRealName());
            userVo.setFreedoms(freedomService.constructForUser(teamMemberUser));
            userVos.add(userVo);
        }
        dataMap.put("id_name_dict", id_name_dict);
        dataMap.put("user_list", userVos);
        dataMap.put("part_free_time", FreeTimeUtils.constructPartFreeTime(userVos));
        result.put("data", dataMap);
        return result;
    }


    // 更新空闲时间
    @PutMapping(value = "api/freedom")
    @ResponseBody // 上传课表
    public HashMap<String, Object> updateFreedom(@RequestAttribute User user,
                                                 @RequestBody FreedomDto freedomDto) {

        HashMap<String, Object> result = new HashMap<>();
        // 删除旧记录
        freedomService.deleteFreedomsByUserIdAndDay(user.getId(), freedomDto.getDay());
        // 添加新记录
        for (Freedom freedom : freedomDto.getFreedoms()) {
            freedom.setUserOwner(user);
            freedomService.addFreedom(freedom);
        }

        result.put("code", 1);
        result.put("msg", "保存成功");
        return result;
    }

    // 更新课表提供教务系统的帐号及密码
    @PostMapping(value = "api/freedom")
    @Transactional
    public HashMap<String, Object> addFreedomByEncryptedData(@RequestAttribute User user, @RequestBody Map map) {
        String encryptedData = (String) map.get("encryptedData");
        HashMap<String, Object> result = new HashMap<>();
        String xlsPath = user.getId() + ".xls";
        System.out.println("课表文件路径: " + xlsPath);
        System.out.println("密文数据：" + encryptedData);
        try {
            // 解密数据
            String plainData = RSAUtils.decryptByPrivateKey(encryptedData);
            plainData = plainData.replace("!", "%%%");
            System.out.println("明文数据：" + plainData);

            // 爬取课表
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("encoded", plainData);
            ScheduleUtils.getSchedule(paramMap, xlsPath);

            // 构造空闲时间
            File xlsFile = new File(xlsPath);
            String[][] xlsData = ExcelUtils.readXls(xlsFile);

            List<Freedom>[] freedoms = ScheduleUtils.getFreedomsByScheduleData(user, xlsData, false);

            if (freedoms == null) {
                result.put("code", -1);
                result.put("msg", "解密出错/登陆不成功");
                return result;
            }
            // 删除旧记录
            freedomService.deleteFreedomsByUser(user);
            // 添加新记录
            for (List<Freedom> list : freedoms) {
                for (Freedom freedom : list) {
                    freedomService.addFreedom(freedom);
                }
            }

            // 删除文件
            if (xlsFile.delete()) {
                System.out.printf("%s 删除成功\n", xlsPath);
            } else {
                System.out.printf("%s 删除失败\n", xlsPath);
            }

            result.put("code", 1);
            result.put("data", freedoms);
            result.put("msg", "成功更新信息");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", -1);
            result.put("msg", "解密出错/登陆不成功");
            return result;
        }
    }

}
