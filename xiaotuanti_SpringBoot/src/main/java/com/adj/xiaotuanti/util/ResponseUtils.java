package com.adj.xiaotuanti.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseUtils {

    /**
     * 描述：输出到前端
     *
     * @author mao2080@sina.com
     * @created 2017年4月28日 上午11:00:25
     * 来源：https://www.cnblogs.com/mao2080/p/6832609.html
     * @modified: Adj325
     */
    public static void printMessage(HttpServletResponse response, Object res) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(res));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
