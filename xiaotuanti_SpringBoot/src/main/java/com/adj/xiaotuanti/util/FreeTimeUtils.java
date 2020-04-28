package com.adj.xiaotuanti.util;

import com.adj.xiaotuanti.pojo.Free;
import com.adj.xiaotuanti.pojo.Freedom;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.pojo.vo.UserVo;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class FreeTimeUtils {
    // 默认空闲时间
    public static int[] defaultFreeTime = {h2m("06:00"), h2m("22:00")};
    // 上课时间
    public static int[][] classTime = {

            {h2m("07:10"), h2m("08:00")}, // 早读

            {h2m("08:20"), h2m("09:55")}, // 第一节课
            {h2m("10:15"), h2m("11:50")}, // 第二节课
            {h2m("11:55"), h2m("12:40")}, // M

            {h2m("14:00"), h2m("15:35")}, // 第三节课
            {h2m("15:40"), h2m("16:25")}, // N
            {h2m("16:35"), h2m("18:10")}, // 第四节课

            {h2m("19:00"), h2m("20:35")}, // 第五节课
    };

    public static String[] classNames = {"第一节", "第二节", "M", "第三节", "N", "第四节", "第五节"};

    // 上课时间
    public static String[][] classTimeStr = {

            {"07:10", "08:00"}, // 早读

            {"08:20", "09:55"}, // 第一节课
            {"10:15", "11:50"}, // 第二节课
            {"11:55", "12:40"}, // M

            {"14:00", "15:35"}, // 第三节课
            {"15:40", "16:25"}, // N
            {"16:30", "18:10"}, // 第四节课

            {"19:00", "20:30"}, // 第五节课
    };

    // 分钟时间
    public static int h2m(String timeHour) {
        String[] tempArr = timeHour.split(":");
        return Integer.parseInt(tempArr[0]) * 60 + Integer.parseInt(tempArr[1]);
    }

    // 时间分钟
    public static String m2h(Integer minutes) {
        Integer hour = minutes / 60;
        Integer minute = minutes % 60;
        return String.format("%02d:%02d", hour, minute);
    }


    public static ArrayList<Free>[] getPartFreeTime(List<User> userList) {
        int dayNum = 7;
        Free free;
        ArrayList<Free>[] frees = new ArrayList[dayNum];
        ArrayList<Free> freeArrayList;
        TreeMap<Integer, TreeMap> endTreeMap;
        TreeMap<Integer, ArrayList<String>> begTreeMap;
        TreeMap<Integer, ArrayList<String>> begTreeMap2;
        ArrayList<String> begArrayList, begArrayList2, begArrayListFree;

        for (int dayId = 0; dayId < dayNum; dayId++) {
            frees[dayId] = new ArrayList<Free>();
            endTreeMap = new TreeMap<Integer, TreeMap>();

            // 统计该天空闲时间段的个数及空闲人员
            for (User user : userList) {
                int[][][] userFreeTime = null;
                if (userFreeTime == null)
                    continue;
                for (int[] partFreeTime : userFreeTime[dayId]) {
                    Integer beg = partFreeTime[0], end = partFreeTime[1];
                    if (!endTreeMap.containsKey(end)) {
                        begTreeMap = new TreeMap<Integer, ArrayList<String>>();
                        begArrayList = new ArrayList<String>();
                    } else {
                        // 用结束时间获取begMap
                        begTreeMap = endTreeMap.get(end);
                        // 用开始时间获取ArrayList
                        begArrayList = begTreeMap.get(beg);

                        if (begArrayList == null)
                            begArrayList = new ArrayList<String>();
                    }
                    begArrayList.add(user.getId());
                    begTreeMap.put(beg, begArrayList);
                    endTreeMap.put(end, begTreeMap);
                }
            }

            // 构建各阶段空闲的人员：遍历的过程中需要更新，为了避免异常决定新建一个TreeMap
            TreeMap<Integer, TreeMap> endTreeMapFree = new TreeMap<Integer, TreeMap>();
            TreeMap<Integer, ArrayList<String>> begTreeMapFree;

            for (Integer endTimeKey : endTreeMap.keySet()) {
                begTreeMap = endTreeMap.get(endTimeKey);
                if (endTreeMapFree.get(endTimeKey) == null)
                    begTreeMapFree = new TreeMap<Integer, ArrayList<String>>();
                else
                    begTreeMapFree = endTreeMapFree.get(endTimeKey);

                for (Integer begTimeKey : begTreeMap.keySet()) {
                    begArrayListFree = new ArrayList<String>();
                    // 跟此前的进行比较
                    for (Integer endTimeKey2 : endTreeMap.keySet()) {
                        // 比较结束时间
                        if (endTimeKey <= endTimeKey2) {
                            begTreeMap2 = endTreeMap.get(endTimeKey2);
                            // 遍历并比较开始时间
                            for (Integer begTimeKey2 : begTreeMap2.keySet()) {
                                if (begTimeKey2 <= begTimeKey) {
                                    begArrayList2 = begTreeMap2.get(begTimeKey2);
                                    for (String userId : begArrayList2) {
                                        if (!begArrayListFree.contains(userId)) {
                                            begArrayListFree.add(userId);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    begTreeMapFree.put(begTimeKey, begArrayListFree);
                    endTreeMapFree.put(endTimeKey, begTreeMapFree);
                }
            }
            // 转为Free数组
            freeArrayList = frees[dayId];
            for (Integer endTimeKey : endTreeMapFree.keySet()) {
                begTreeMapFree = endTreeMapFree.get(endTimeKey);
                for (Integer begTimeKey : begTreeMapFree.keySet()) {
                    begArrayList = begTreeMapFree.get(begTimeKey);
                    free = new Free();
                    free.setBegTime(begTimeKey);
                    free.setEndTime(endTimeKey);
                    free.setBegTimeStr(m2h(begTimeKey));
                    free.setEndTimeStr(m2h(endTimeKey));
                    free.setLast(endTimeKey - begTimeKey);
                    free.setType(0);
                    free.setUserIds(begArrayList);
                    // 占比
                    double proportion = 1.0 * begArrayList.size() / userList.size();
                    free.setProportion(String.format("%.0f", proportion * 100));
                    freeArrayList.add(free);
                }
            }

        }

        return frees;
    }

    @Deprecated
    public static ArrayList<Free>[][] getScheduleFreeTime(List<User> userList) {
        int weekTypeNum = 3, dayNum = 7, partNum = 7;
        Free free;
        ArrayList<String> userIds;
        ArrayList<Free> freeArrayList;
        ArrayList<Free>[][] frees = new ArrayList[weekTypeNum][dayNum];
        for (int weekType = 0; weekType < 3; weekType++) {
            frees[weekType] = new ArrayList[dayNum];
            for (int dayId = 0; dayId < dayNum; dayId++) {
                frees[weekType][dayId] = new ArrayList<Free>();
                freeArrayList = frees[weekType][dayId];
                for (int partId = 0; partId < partNum; partId++) {
                    userIds = new ArrayList<String>();
                    for (User user : userList) {
                        JSONArray jsonArray = null;
                        if (jsonArray == null)
                            continue;
                        boolean statue = jsonArray.getJSONArray(weekType).getJSONArray(dayId).getBoolean(partId);
                        if (!statue) {
                            userIds.add(user.getId());
                        }
                    }

                    free = new Free();
                    free.setBegTime(classTime[partId + 1][0]);
                    free.setEndTime(classTime[partId + 1][1]);
                    free.setBegTimeStr(classTimeStr[partId + 1][0]);
                    free.setEndTimeStr(classTimeStr[partId + 1][1]);
                    free.setLast(classTime[partId + 1][1] - classTime[partId + 1][0]);
                    free.setType(1);
                    free.setName(classNames[partId]);
                    free.setUserIds(userIds);
                    // 占比
                    double proportion = 1.0 * userIds.size() / userList.size();
                    free.setProportion(String.format("%.0f", proportion * 100));
                    freeArrayList.add(free);
                }
            }
        }
        return frees;
    }

    // 空闲时间转空闲时间
    public static int[][][][] getFreeTime(ArrayList<Boolean>[][] scheduleStatue) {
        int daysNum = scheduleStatue[0].length;
        List<Integer>[][] freeTimeList = new List[3][daysNum];
        int[][][][] freeTimeArr = new int[3][daysNum][][];

        // 添加默认空闲时间
        for (int weekType = 0; weekType < 3; weekType++) {
            for (int dayId = 0; dayId < daysNum; dayId++) {
                freeTimeList[weekType][dayId] = new ArrayList();
                // 添加默认空闲时段
                freeTimeList[weekType][dayId].add(defaultFreeTime[0]);
                freeTimeList[weekType][dayId].add(defaultFreeTime[1]);
            }
        }
        // 课表数字转空闲时间
        for (int weekType = 0; weekType < 3; weekType++) {
            for (int dayId = 0; dayId < daysNum; dayId++) {
                //System.out.printf("周：%s, 星期%s\n", weekType, dayId);
                for (int clsId = 0; clsId < scheduleStatue[weekType][dayId].size(); clsId++) {
                    if (scheduleStatue[weekType][dayId].get(clsId)) {
                        freeTimeList[weekType][dayId].add(classTime[clsId][0]);
                        freeTimeList[weekType][dayId].add(classTime[clsId][1]);
                    }
                }

                // 从小到大排序
                Collections.sort(freeTimeList[weekType][dayId]);
                // 两个一组
                freeTimeArr[weekType][dayId] = new int[freeTimeList[weekType][dayId].size() / 2][2];
                for (int i = 0; i < freeTimeArr[weekType][dayId].length; i++) {
                    freeTimeArr[weekType][dayId][i][0] = freeTimeList[weekType][dayId].get(i * 2);
                    freeTimeArr[weekType][dayId][i][1] = freeTimeList[weekType][dayId].get(i * 2 + 1);
                }

            }
        }

        return freeTimeArr;
    }

    public static List<Free>[] constructPartFreeTime(List<UserVo> userVos) {

        int dayNum = 7;
        Free free;
        List<Free>[] frees = new ArrayList[dayNum];
        ArrayList<Free> freeArrayList;
        TreeMap<Integer, TreeMap> endTreeMap;
        TreeMap<Integer, ArrayList<String>> begTreeMap;
        TreeMap<Integer, ArrayList<String>> begTreeMap2;
        ArrayList<String> begArrayList, begArrayList2, begArrayListFree;

        for (int dayId = 0; dayId < dayNum; dayId++) {
            frees[dayId] = new ArrayList<Free>();
            endTreeMap = new TreeMap<Integer, TreeMap>();

            // 统计该天空闲时间段的个数及空闲人员
            for (UserVo user : userVos) {
                Freedom[][] userFreedoms = user.getFreedoms();
                if (userFreedoms == null){
                    continue;
                }
                for (Freedom partFreedom : userFreedoms[dayId]) {
                    Integer beg = partFreedom.getBegTime(), end = partFreedom.getEndTime();
                    if (!endTreeMap.containsKey(end)) {
                        begTreeMap = new TreeMap<Integer, ArrayList<String>>();
                        begArrayList = new ArrayList<String>();
                    } else {
                        // 用结束时间获取begMap
                        begTreeMap = endTreeMap.get(end);
                        // 用开始时间获取ArrayList
                        begArrayList = begTreeMap.get(beg);

                        if (begArrayList == null)
                            begArrayList = new ArrayList<String>();
                    }
                    begArrayList.add(user.getId());
                    begTreeMap.put(beg, begArrayList);
                    endTreeMap.put(end, begTreeMap);
                }
            }

            // 构建各阶段空闲的人员：遍历的过程中需要更新，为了避免异常决定新建一个TreeMap
            TreeMap<Integer, TreeMap> endTreeMapFree = new TreeMap<Integer, TreeMap>();
            TreeMap<Integer, ArrayList<String>> begTreeMapFree;

            for (Integer endTimeKey : endTreeMap.keySet()) {
                begTreeMap = endTreeMap.get(endTimeKey);
                if (endTreeMapFree.get(endTimeKey) == null)
                    begTreeMapFree = new TreeMap<Integer, ArrayList<String>>();
                else
                    begTreeMapFree = endTreeMapFree.get(endTimeKey);

                for (Integer begTimeKey : begTreeMap.keySet()) {
                    begArrayListFree = new ArrayList<String>();
                    // 跟此前的进行比较
                    for (Integer endTimeKey2 : endTreeMap.keySet()) {
                        // 比较结束时间
                        if (endTimeKey <= endTimeKey2) {
                            begTreeMap2 = endTreeMap.get(endTimeKey2);
                            // 遍历并比较开始时间
                            for (Integer begTimeKey2 : begTreeMap2.keySet()) {
                                if (begTimeKey2 <= begTimeKey) {
                                    begArrayList2 = begTreeMap2.get(begTimeKey2);
                                    for (String userId : begArrayList2) {
                                        if (!begArrayListFree.contains(userId)) {
                                            begArrayListFree.add(userId);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    begTreeMapFree.put(begTimeKey, begArrayListFree);
                    endTreeMapFree.put(endTimeKey, begTreeMapFree);
                }
            }
            // 转为Free数组
            freeArrayList = (ArrayList<Free>) frees[dayId];
            for (Integer endTimeKey : endTreeMapFree.keySet()) {
                begTreeMapFree = endTreeMapFree.get(endTimeKey);
                for (Integer begTimeKey : begTreeMapFree.keySet()) {
                    begArrayList = begTreeMapFree.get(begTimeKey);
                    free = new Free();
                    free.setBegTime(begTimeKey);
                    free.setEndTime(endTimeKey);
                    free.setBegTimeStr(m2h(begTimeKey));
                    free.setEndTimeStr(m2h(endTimeKey));
                    free.setLast(endTimeKey - begTimeKey);
                    free.setType(0);
                    free.setUserIds(begArrayList);
                    // 占比
                    double proportion = 1.0 * begArrayList.size() / userVos.size();
                    free.setProportion(String.format("%.0f", proportion * 100));
                    freeArrayList.add(free);
                }
            }

        }

        return frees;
    }
}
