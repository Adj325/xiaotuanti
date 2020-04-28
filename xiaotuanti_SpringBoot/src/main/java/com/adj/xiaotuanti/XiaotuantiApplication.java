package com.adj.xiaotuanti;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "com.adj.xiaotuanti.controller",
        "com.adj.xiaotuanti.configure",
        "com.adj.xiaotuanti.dao",
        "com.adj.xiaotuanti.handler",
        "com.adj.xiaotuanti.interceptor",
        "com.adj.xiaotuanti.pojo",
        "com.adj.xiaotuanti.service",
        "com.adj.xiaotuanti.util",
})
@MapperScan("com.adj.xiaotuanti.dao")
@SpringBootApplication
public class XiaotuantiApplication {

	public static void main(String[] args) {
		SpringApplication.run(XiaotuantiApplication.class, args);
	}

}
