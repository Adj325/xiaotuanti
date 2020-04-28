package com.adj.xiaotuanti.util;

import com.adj.xiaotuanti.pojo.Freedom;
import com.adj.xiaotuanti.pojo.User;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleUtils {

    private static int MIN_LAST_CLASS_TIME = 0;

    public static String getSchedule(HashMap<String, String> map, String outputPath) {
        // 课表下载Url
        String scheduleXlsUrl = "http://jwxt.gduf.edu.cn/jsxsd/xskb/xskb_print.do?xnxq01id=2019-2020-2&zc=";

        try {
            CookieStore cookieStore = new BasicCookieStore();

            // 创建客户端
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

            // 构造post
            String loginUrl = "http://jwxt.gduf.edu.cn/jsxsd/xk/LoginToXk";
            HttpPost httpPost = new HttpPost(loginUrl);
            // 添加登陆encoded参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
                httpPost.setEntity(entity);
            }
            // 执行post，进行登陆
            httpClient.execute(httpPost);

            // 构造get
            HttpGet httpGet = new HttpGet(scheduleXlsUrl);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            // 输出为文件
            InputStream is = entity.getContent();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int r = 0;
            while ((r = is.read(buffer)) > 0) {
                output.write(buffer, 0, r);
            }
            FileOutputStream fos = new FileOutputStream(outputPath);
            output.writeTo(fos);
            output.flush();
            output.close();
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    // 读取课表文件，获取课表情况
    public static HashMap<String, Object> readSchedule(String[][] xlsData, Boolean isExistMorningReading) {

        // 内容为空
        if (xlsData == null) {
            return null;
        } else {
            int partsNum = 7, daysNum = 7, dayPos = 1;
            // 提取xlsData关键数据
            String[][] partsData = new String[partsNum][8];
            try {
                // 新课表
                for (int i = 0; i < 7; i++) {
                    partsData[i] = xlsData[3 + i];
                }
            } catch (Exception e) {
                System.out.println("警告：此为旧课表！\n");
                System.exit(1);
            }

            // 学生名字
            String studentName = xlsData[0][0].split(" ")[1];

            // 课表状态 单双全周, 每周七天
            ArrayList<Boolean>[][] scheduleStatue = new ArrayList[3][daysNum];
            // 初始化
            for (int weekType = 0; weekType < scheduleStatue.length; weekType++) {
                scheduleStatue[weekType] = new ArrayList[daysNum];
                for (int dayId = 0; dayId < daysNum; dayId++) {
                    scheduleStatue[weekType][dayId] = new ArrayList();
                    // 添加早读状态
                    scheduleStatue[weekType][dayId].add(isExistMorningReading);
                }
            }

            // 遍历课表
            for (int dayId = 0; dayId < daysNum; dayId++) {
                for (int clsId = 0; clsId < partsNum; clsId++) {
                    String curClass = partsData[clsId][dayId + dayPos].substring(1);
                    // 特殊情况
                    if (curClass.contains("无法值班") || curClass.contains("没空")) {
                        scheduleStatue[0][dayId].add(true);
                        scheduleStatue[1][dayId].add(true);
                        scheduleStatue[2][dayId].add(true);
                        continue;
                    }

                    // 字符串长度过小, 小于3, 默认没课!
                    if (curClass.length() < 3) {
                        scheduleStatue[0][dayId].add(false);
                        scheduleStatue[1][dayId].add(false);
                        scheduleStatue[2][dayId].add(false);
                        continue;
                    }

                    // 当前节的所有课程，均以\n\n为间隔隔开
                    // 遍历当前节的的所有课程，判断其类型及持续
                    String[] classes = curClass.split("\n\n");

                    boolean[] classStatue = new boolean[3];
                    //System.out.print("\n"+(dayId+1)+" "+(clsId+1)+"\n");
                    for (String classContent : classes) {
                        //System.out.println(classContent);
                        String weekLine = classContent.split("\n")[2];
                        boolean isExistsClass = false;
                        if (weekLine.contains(",")) {
                            isExistsClass = true;
                        } else {
                            // 根据上课持续时间，判断有没有课程
                            Pattern r = Pattern.compile("(\\d+)-(\\d+)");
                            Matcher m = r.matcher(weekLine);
                            if (m.find()) {

                                String[] weekNum = m.group().split("-");
                                int lastTime = Integer.parseInt(weekNum[1]) - Integer.parseInt(weekNum[0]);

                                // 当上课时长超过10周, 有课
                                if (lastTime > 4) {
                                    isExistsClass = true;
                                }
                            }
                        }
                        if (isExistsClass) {
                            String[] classSigns = {"[单周", "[双周", "[周"};
                            for (int classSignId = 0; classSignId < classSigns.length; classSignId++)
                                if (classContent.contains(classSigns[classSignId])) {
                                    classStatue[classSignId] = true;
                                }
                        }
                    }
                    // 添加课程状态
                    if (classStatue[0] && classStatue[1]) {
                        // 单双周有课
                        scheduleStatue[0][dayId].add(true);
                        scheduleStatue[1][dayId].add(true);
                        scheduleStatue[2][dayId].add(true);
                        //System.out.println("单双周有课");
                    } else if (classStatue[2]) {
                        // 全周有课
                        scheduleStatue[0][dayId].add(true);
                        scheduleStatue[1][dayId].add(true);
                        scheduleStatue[2][dayId].add(true);
                        //System.out.println("全周有课");
                    } else {
                        // 单周
                        if (classStatue[0]) {
                            scheduleStatue[0][dayId].add(true);
                            //System.out.println("单周有课");
                        } else {
                            scheduleStatue[0][dayId].add(false);
                            //System.out.println("单周无课");
                        }
                        // 双周
                        if (classStatue[1]) {
                            scheduleStatue[1][dayId].add(true);
                            //System.out.println("双周有课");
                        } else {
                            scheduleStatue[1][dayId].add(false);
                            //System.out.println("双周无课");
                        }
                        // 全周
                        if (classStatue[0] || classStatue[1]) {

                            // 全周有课：单双周其中一个有课
                            scheduleStatue[2][dayId].add(true);
                            //System.out.println("全周有课：单双周其中一个有课");
                        } else {
                            // 全周无课：单双周均无课
                            scheduleStatue[2][dayId].add(false);
                            //System.out.println("全周有课：单双周均无课");
                        }
                    }
                }
            }

            HashMap<String, Object> hashMap = new HashMap();

            // 将课表状态转换为空闲时间
            int[][][][] freeTimeArr = FreeTimeUtils.getFreeTime(scheduleStatue);
            hashMap.put("freeTime", freeTimeArr);
            hashMap.put("scheduleStatue", scheduleStatue);
            hashMap.put("name", studentName);

            return hashMap;
        }
    }

    public static List<Freedom>[] getFreedomsByScheduleData(User user, String[][] xlsData, Boolean isExistMorningReading) {

        // 内容为空
        if (xlsData == null) {
            return null;
        } else {
            int partsNum = 7, daysNum = 7, dayPos = 1;
            // 提取xlsData关键数据
            String[][] partsData = new String[partsNum][8];
            try {
                // 新课表
                for (int i = 0; i < 7; i++) {
                    partsData[i] = xlsData[3 + i];
                }
            } catch (Exception e) {
                System.out.println("警告：此为旧课表！\n");
                System.exit(1);
            }

            // 课表状态 单双全周, 每周七天
            ArrayList<Boolean>[][] scheduleStatue = new ArrayList[3][daysNum];
            // 初始化
            for (int weekType = 0; weekType < scheduleStatue.length; weekType++) {
                scheduleStatue[weekType] = new ArrayList[daysNum];
                for (int dayId = 0; dayId < daysNum; dayId++) {
                    scheduleStatue[weekType][dayId] = new ArrayList();
                    // 添加早读状态
                    scheduleStatue[weekType][dayId].add(isExistMorningReading);
                }
            }

            // 遍历课表
            for (int dayId = 0; dayId < daysNum; dayId++) {
                for (int clsId = 0; clsId < partsNum; clsId++) {
                    String curClass = partsData[clsId][dayId + dayPos].substring(1);
                    // 特殊情况
                    if (curClass.contains("无法值班") || curClass.contains("没空")) {
                        scheduleStatue[0][dayId].add(true);
                        scheduleStatue[1][dayId].add(true);
                        scheduleStatue[2][dayId].add(true);
                        continue;
                    }

                    // 字符串长度过小, 小于3, 默认没课!
                    if (curClass.length() < 3) {
                        scheduleStatue[0][dayId].add(false);
                        scheduleStatue[1][dayId].add(false);
                        scheduleStatue[2][dayId].add(false);
                        continue;
                    }

                    // 当前节的所有课程，均以\n\n为间隔隔开
                    // 遍历当前节的的所有课程，判断其类型及持续
                    String[] classes = curClass.split("\n\n");

                    boolean[] classStatue = new boolean[3];
                    //System.out.print("\n"+(dayId+1)+" "+(clsId+1)+"\n");
                    for (String classContent : classes) {
//                        System.out.println(classContent);
                        String weekLine = classContent.split("\n")[2];
                        boolean isExistsClass = false;
                        if (weekLine.contains(",")) {
                            isExistsClass = true;
                        } else {
                            // 根据上课持续时间，判断有没有课程
                            Pattern r = Pattern.compile("(\\d+)-(\\d+)");
                            Matcher m = r.matcher(weekLine);
                            if (m.find()) {

                                String[] weekNum = m.group().split("-");
                                int lastTime = Integer.parseInt(weekNum[1]) - Integer.parseInt(weekNum[0]);

                                // 当上课时长超过10周, 有课
                                if (lastTime > MIN_LAST_CLASS_TIME) {
                                    isExistsClass = true;
                                }
                            }
                        }
                        if (isExistsClass) {
                            String[] classSigns = {"[单周", "[双周", "[周"};
                            for (int classSignId = 0; classSignId < classSigns.length; classSignId++)
                                if (classContent.contains(classSigns[classSignId])) {
                                    classStatue[classSignId] = true;
                                }
                        }
                    }

                    // 添加课程状态
                    if (classStatue[0] && classStatue[1]) {
                        // 单双周有课
                        scheduleStatue[0][dayId].add(true);
                        scheduleStatue[1][dayId].add(true);
                        scheduleStatue[2][dayId].add(true);
//                        System.out.println("单双周有课");
                    } else if (classStatue[2]) {
                        // 全周有课
                        scheduleStatue[0][dayId].add(true);
                        scheduleStatue[1][dayId].add(true);
                        scheduleStatue[2][dayId].add(true);
//                        System.out.println("全周有课");
                    } else {
                        // 单周
                        if (classStatue[0]) {
                            scheduleStatue[0][dayId].add(true);
//                            System.out.println("单周有课");
                        } else {
                            scheduleStatue[0][dayId].add(false);
//                            System.out.println("单周无课");
                        }
                        // 双周
                        if (classStatue[1]) {
                            scheduleStatue[1][dayId].add(true);
//                            System.out.println("双周有课");
                        } else {
                            scheduleStatue[1][dayId].add(false);
//                            System.out.println("双周无课");
                        }
                        // 全周
                        if (classStatue[0] || classStatue[1]) {
                            // 全周有课：单双周其中一个有课
                            scheduleStatue[2][dayId].add(true);
//                            System.out.println("全周有课：单双周其中一个有课");
                        } else {
                            // 全周无课：单双周均无课
                            scheduleStatue[2][dayId].add(false);
//                            System.out.println("全周无课：单双周均无课");
                        }
                    }
                }
            }

            // 将课表状态转换为空闲时间
            int[][][][] freeTimeArr = FreeTimeUtils.getFreeTime(scheduleStatue);
            List<Freedom>[] freedomLists = new ArrayList[7];
            int day = 1;
            for (int[][] array1 : freeTimeArr[2]) {
                List<Freedom> freedoms = new ArrayList<Freedom>();
                for (int[] array2 : array1) {
                    freedoms.add(new Freedom(user, day, array2[0], array2[1]));
                }
                freedomLists[day - 1] = freedoms;
                day++;
            }
            return freedomLists;
        }
    }

    /***
     *
     * @param xlsFilePath : 课表名, 一般是学号
     * @return          : 读取成功, 返回的二维String数组; 否则, 返回null
     *
     */
    public static HashMap<String, Object> readSchedule(String xlsFilePath, Boolean isExistMorningReading) {

        String[][] xlsData = ExcelUtils.readXls(xlsFilePath);
        if (xlsData != null)
            return readSchedule(xlsData, isExistMorningReading);
        else
            return null;
    }

    public static HashMap<String, Object> readSchedule(File xlsFile, Boolean isExistMorningReading) {

        String[][] xlsData = ExcelUtils.readXls(xlsFile);
        if (xlsData != null)
            return readSchedule(xlsData, isExistMorningReading);
        else
            return null;
    }

    public static void main(String[] args) {
        File xlsFile = new File("D:\\@项目\\@小团体\\xiaotuanti_SpringBoot\\2342.xls");
        String[][] xlsData = ExcelUtils.readXls(xlsFile);
        List<Freedom>[] freedoms = ScheduleUtils.getFreedomsByScheduleData(null, xlsData, true);
        System.out.println();
    }
}
