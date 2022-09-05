package com.github.peacetrue.bee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Bee 主应用。
 *
 * @author peace
 **/
@SpringBootApplication
public class BeeMainApplication {

    /**
     * 程序入口方法。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(BeeMainApplication.class, args);
    }

    /**
     * 记录请求日志。
     *
     * @return 日志过滤器
     */
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(1024 * 1024);
        loggingFilter.setIncludeHeaders(false);
        return loggingFilter;
    }
}
