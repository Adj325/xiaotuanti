package com.adj.xiaotuanti.configure;

import com.adj.xiaotuanti.interceptor.TokenLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class InterceptorConfigure implements WebMvcConfigurer {

    private final TokenLoginInterceptor tokenLoginInterceptor;

    @Autowired
    public InterceptorConfigure(TokenLoginInterceptor tokenLoginInterceptor) {
        this.tokenLoginInterceptor = tokenLoginInterceptor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 拦截127.0.0.1:8080/context-path/static/**，将**转发到classpath:/static/
        // 同时需要在拦截器放行 /static/**
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录
        registry.addInterceptor(tokenLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/webSocket/socketServer",
                        "/api/users/login",
                        "/api/users/isLogin",
                        "/api/bins/getBin"
                );
    }

    /*
    * 如果不是 spring boot 项目，那就不需要进行这样的配置，因为如果在 tomcat 中运行的话，tomcat 会扫描带有 @ServerEndpoint 的注解成为 websocket，而 spring boot 项目中需要由这个 bean 来提供注册管理。
    *\*/
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}