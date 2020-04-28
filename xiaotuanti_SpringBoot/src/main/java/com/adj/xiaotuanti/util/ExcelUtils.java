package com.adj.xiaotuanti.util;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.*;

/**
 * Created by Adj on 2019-5-30.
 */
public class ExcelUtils {
    // 读取xls文件

    /**
     * read: 读取名为excelName的xls文件(只读取第一个sheet)
     *
     * @param xlsFilePath : 被读取的xls文件名
     * @return : 读取成功, 返回的二维String数组; 否则, 返回null
     */
    public static String[][] readXls(String xlsFilePath) {
        try {
            // 获取xls文件实例
            File xlsFile = new File(xlsFilePath);
            return readXls(xlsFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[][] readXls(File xlsFile) {
        try {
            // xls数据
            String[][] excelStringArr = null;

            // 获得工作簿对象
            Workbook workbook = Workbook.getWorkbook(xlsFile);

            // 获得所有工作表
            Sheet[] sheets = workbook.getSheets();
            // 默认返回工作表1
            // 工作表有数据
            if (sheets != null) {
                // 获得行数
                int rows = sheets[0].getRows();
                // 获得列数
                int cols = sheets[0].getColumns();

                excelStringArr = new String[rows][cols];
                // 读取数据
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        excelStringArr[row][col] = sheets[0].getCell(col, row).getContents();
                    }
                }
            }
            workbook.close();
            return excelStringArr;
        } catch (Exception e) {

            System.out.println(xlsFile.getName());
            e.printStackTrace();
            return null;
        }
    }

    // 输出xls文件

    /**
     * write: 创建名为excelName的xls文件
     *
     * @param xlsFilePath : String, xls文件名, 不用带后缀
     * @param xlsData     : String[][], xls的内容, 二维数组
     * @return boolean        : 操作成功, 返回true; 否则, 返回false
     */
    public static boolean writeXls(String xlsFilePath, String[][] xlsData) {
        String xlsName = "Test";
        if (!xlsFilePath.equals(""))
            xlsName = xlsFilePath;
        try {
            File xlsFile = new File(xlsName);
            // 创建一个工作簿
            WritableWorkbook workbook = Workbook.createWorkbook(xlsFile);
            // 创建一个工作表
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            // 遍历excelStringArr数组
            for (int row = 0; row < xlsData.length; row++) {
                for (int col = 0; col < xlsData[row].length; col++) {
                    // 向工作表中添加数据
                    sheet.addCell(new Label(col, row, xlsData[row][col]));
                }
            }
            workbook.write();
            workbook.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取空闲时间
    public static int[][][][] getFreeTime(ArrayList<Boolean>[][] scheduleStatue) {
        int daysNum = scheduleStatue[0].length;
        List<Integer>[][] freeTimeList = new List[3][daysNum];
        int[][][][] freeTimeArr = new int[3][daysNum][][];

        // 添加默认空闲时间
        for (int weekType = 0; weekType < 3; weekType++) {
            for (int dayId = 0; dayId < daysNum; dayId++) {
                freeTimeList[weekType][dayId] = new ArrayList();
                // 添加默认空闲时段
                freeTimeList[weekType][dayId].add(FreeTimeUtils.defaultFreeTime[0]);
                freeTimeList[weekType][dayId].add(FreeTimeUtils.defaultFreeTime[1]);
            }
        }
        // 课表数字转空闲时间
        for (int weekType = 0; weekType < 3; weekType++) {
            for (int dayId = 0; dayId < daysNum; dayId++) {
                //System.out.printf("周：%s, 星期%s\n", weekType, dayId);
                for (int clsId = 0; clsId < scheduleStatue[weekType][dayId].size(); clsId++) {
                    if (scheduleStatue[weekType][dayId].get(clsId)) {
                        freeTimeList[weekType][dayId].add(FreeTimeUtils.classTime[clsId][0]);
                        freeTimeList[weekType][dayId].add(FreeTimeUtils.classTime[clsId][1]);
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


}
