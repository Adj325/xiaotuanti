package com.adj.xiaotuanti.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    //@ExceptionHandler({Exception.class})    // 申明捕获那个异常类
    public HashMap<String, Object> globalExceptionHandler(Exception e) {
        this.logger.error(e.getMessage(), e);
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "-1");
        result.put("msg", "出现错误！");
        return result;
    }

}