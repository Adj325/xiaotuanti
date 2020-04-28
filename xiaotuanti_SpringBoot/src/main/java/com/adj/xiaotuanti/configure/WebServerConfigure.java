package com.adj.xiaotuanti.configure;

import org.apache.catalina.valves.AccessLogValve;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.nio.charset.Charset;

@SpringBootConfiguration
public class WebServerConfigure {

    @Bean
    public AbstractServletWebServerFactory createServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        // tomcat端口
        // factory.setPort(8080);
        // 设置状态码对应的错误页面
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
        // 设置日志
        //  factory.addContextValves(getLogAccessLogValue());
        factory.setUriEncoding(Charset.forName("UTF-8"));
        factory.addConnectorCustomizers(
                (connector -> {
                    connector.setPort(8080);
                    Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
                    // 最大工作线程数，默认 200
                    // 4核8g内存，线程数经验值800，操作系统做线程之间的切换调度是有系统开销的，所以不是越多越好。
                    protocol.setMaxThreads(500);
                    // 最小空闲线程数，默认 10
                    // 适当增大一些，以便应对突然增长的访问量。
                    protocol.setMaxThreads(500);
                    // 等待队列长度，默认 100
                    protocol.setAcceptCount(1000);

                    // 最大连接数，1000
                    protocol.setMaxConnections(1000);
                    // 多少毫秒后不响应，断开 KeepAlive
                    protocol.setKeepAliveTimeout(30 * 1000);
                    // 多少次请求后，断开 KeepAlive
                    protocol.setMaxKeepAliveRequests(10000);

                })
        );
        // tomcat容器初始化时执行
        factory.addInitializers(new MyServletContext());
        return factory;
    }

    private AccessLogValve getLogAccessLogValue() {
        AccessLogValve log = new AccessLogValve();
//        log.setDirectory("d:/temp/logs");//日志路径
        log.setEnabled(true);//启用日志
        log.setPattern("common");//输入日志格式
        log.setPrefix("springboot-access-log");//日志名称
        log.setSuffix(".txt");//日志后缀
        return log;
    }

    class MyServletContext implements ServletContextInitializer {
        @Override
        public void onStartup(ServletContext servletContext)
                throws ServletException {
        }
    }
}

