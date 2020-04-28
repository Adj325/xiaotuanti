package com.adj.xiaotuanti.configure;

import com.adj.xiaotuanti.handler.SocketHandler;
import com.adj.xiaotuanti.interceptor.TokenLoginInterceptor;
import com.adj.xiaotuanti.interceptor.WebSocketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
@Component
@EnableWebSocket
public class WebSocketInterceptorConfigure extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    private final WebSocketInterceptor webSocketInterceptor;

    @Autowired
    public WebSocketInterceptorConfigure(WebSocketInterceptor webSocketInterceptor) {
        this.webSocketInterceptor = webSocketInterceptor;
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        String[] allowedOrigins = {
                "*",
                "http://127.0.0.1",
                "http://127.0.0.1:*",
        };
        //这里的url要与页面的url一致
        // .setAllowedOrigins("*") 解决403
        webSocketHandlerRegistry.addHandler(myHandler(), "/webSocket/socketServer").
                addInterceptors(webSocketInterceptor).
                setAllowedOrigins(allowedOrigins);
        // 至于这里为什么要加info，我遇见的情况是，当我使用sockjs来代替websocket时，连接的后面会自动加上info
        webSocketHandlerRegistry.addHandler(
                myHandler(), "/webSocket/sockjs/socketServer").
                addInterceptors(webSocketInterceptor).withSockJS();
    }

    @Bean
    public SocketHandler myHandler() {
        return new SocketHandler();
    }

}