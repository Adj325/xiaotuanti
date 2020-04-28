package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.*;
import com.adj.xiaotuanti.pojo.constants.MemberLevel;
import com.adj.xiaotuanti.service.*;
import com.adj.xiaotuanti.util.ExcelUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@RestController
public class XlsController {


    private final PartService partService;
    private final UserService userService;
    private final TeamService teamService;
    private final MemberService memberService;
    private final ActivityService activityService;

    @Autowired
    public XlsController(PartService partService, UserService userService, TeamService teamService, MemberService memberService, ActivityService activityService) {
        this.partService = partService;
        this.userService = userService;
        this.teamService = teamService;
        this.memberService = memberService;
        this.activityService = activityService;
    }

    @RequestMapping(value = "api/xls")
    @ResponseBody
    public void xls(
            @RequestAttribute User user,
            @RequestParam("type") String type,
            @RequestParam("teamId") String teamId,
            @RequestParam("activityId") String activityId,
            HttpServletResponse response) throws Exception {
        if (type == null)
            return;
        switch (type){
            case "activity":
                activity(response, user, activityId);
                break;
            case "contact":
                contact(response, user, teamId);
                break;
            case "schedule":
                schedule(response, user);
                break;
            default:
                break;
        }
    }

    private void activity(HttpServletResponse response, User user, String activityId) {
        Activity activity = activityService.getActivityById(activityId);
        if (activity == null)
            return;
        Team team = activity.getTeam();
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), team.getId());
        if (member == null)
            return;

        // 第一步：构建下载文件
        List<Part> parts = partService.getPartsByActivityId(activityId);
        String[][] xlsData = new String[5 + parts.size()][];
        xlsData[0] = new String[]{"活动名称", activity.getName()};
        //xlsData[1] = new String[]{"活动性质" + " " + activity.getTypeName()};
        xlsData[2] = new String[]{"举办方", team.getName()};
        xlsData[3] = new String[]{"举办日期", activity.getBegTime() + " ~ " + activity.getEndTime()};
        //xlsData[4] = new String[]{"举办星期", activity.getWeekTypeName() + " " + activity.getWeekDayName()};
        for (int i = 0; i < parts.size(); i++) {
            Part part = parts.get(i);
            JSONArray officialsJSONArray = null;// todo 修改 part.getOfficials();
            StringBuilder officials = new StringBuilder();

            for (int idx = 0; idx < officialsJSONArray.size(); idx++) {
                String tempUserId = officialsJSONArray.getString(idx);
                User tempUser = userService.getUserById(tempUserId);
                officials.append(tempUser.getRealName()).append(" ");
            }
            String partName = String.format("阶段%d\n%s ~ %s", i + 1, part.getBegTimeStr(), part.getEndTimeStr());
            xlsData[5 + i] = new String[]{partName, officials.toString()};
        }

        String filename = activity.getName() + "_" + activityId + ".xls";
        System.out.println(JSON.toJSONString(xlsData));
        ExcelUtils.writeXls(filename, xlsData);
        output(response, filename);
    }
    private void contact(HttpServletResponse response, User user, String teamId){
        Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), teamId);
        if (member == null)
            return;
        // 第一步：构建下载文件
        Team team = teamService.getTeamById(teamId);
        List<Member> memberList = memberService.getMembersByTeamId(teamId);
        String[][] xlsData = new String[memberList.size() + 2][];
        xlsData[0] = new String[1];
        xlsData[0][0] = team.getName() + " 通讯录";
        xlsData[1] = new String[]{
                "部门", "职务", "姓名", "性别", "年级",
                "学号", "QQ", "手机", "微信", "邮箱",
                "宿舍", "籍贯", "Spec"};
        xlsData[0][0] = team.getName() + " 通讯录";
        String[] sexNames = {"女", "男", "?"};
        String[] gradeNames = {"大一", "大二", "大三", "大四"};
        for (int i = 0, xlsId = 2; i < memberList.size(); i++, xlsId++) {
            Member tempMember = memberList.get(i);
            User tempUser = tempMember.getUser();
            xlsData[xlsId] = new String[13];
            if (tempMember.getSection() != null)
                xlsData[xlsId][0] = tempMember.getSection().getName();
            else
                xlsData[xlsId][0] = "无部门";
            xlsData[xlsId][1] = tempMember.getJob();
            xlsData[xlsId][2] = tempUser.getRealName();
            xlsData[xlsId][3] = sexNames[tempUser.getGender()];
            xlsData[xlsId][4] = gradeNames[tempUser.getGrade()];
            xlsData[xlsId][5] = tempMember.getStuno();
            xlsData[xlsId][6] = tempMember.getQq();
            xlsData[xlsId][7] = tempMember.getWeixin();
            xlsData[xlsId][8] = tempMember.getPhone();
            xlsData[xlsId][9] = tempMember.getEmail();
            xlsData[xlsId][10] = tempMember.getDormitory();
            xlsData[xlsId][11] = tempMember.getAddress();
            if (member.getLevel().ordinal() >= MemberLevel.TEAM_MANAGER.ordinal())
                xlsData[xlsId][12] = tempMember.getSpecial();
        }
        String filename = teamId + ".xls";
        ExcelUtils.writeXls(filename, xlsData);

        output(response, filename);
    }
    private void schedule(HttpServletResponse response, User user){
        String filename = user.getId() + "_schedule.xls";
        String[] weekTypeNames = {"单周", "双周", "全周"};
        String[] partNames = {"早读", "第一节", "第二节", "M", "第三节", "N", "第四节", "第五节"};
        Integer weekTypeNums = 3;
        Integer dayNums = 7;
        Integer partNums = 7;
        String[][] xlsData = new String[30][9];
        for (int i = 0; i < weekTypeNums; i++) {
            xlsData[10 * i][0] = weekTypeNames[i] + " 无课表";
            xlsData[10 * i + 1] = new String[]{
                    "", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"
            };

            for (int dayId = 0; dayId < dayNums; dayId++) {
                for (int partId = 0; partId < partNums; partId++) {
                    boolean statue = true;
                    if (!statue) {
                        xlsData[10 * i + partId + 2][dayId + 1] = user.getRealName();
                    }
                }
            }

            for (int partId = 0; partId < 8; partId++) {
                xlsData[10 * i + partId + 2][0] = partNames[partId];
            }
        }

        ExcelUtils.writeXls(filename, xlsData);
        output(response, filename);
    }
    private void output(HttpServletResponse response, String filename){
        try{
            // 获取输入流
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(filename)));
            // 转码，免得文件名中文乱码
            filename = URLEncoder.encode(filename, "UTF-8");
            // 设置文件下载头
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);

            // 使用缓冲数组加快上传速度
            byte buffer[] = new byte[1024 * 8];
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            int len;
            while ((len = bis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
