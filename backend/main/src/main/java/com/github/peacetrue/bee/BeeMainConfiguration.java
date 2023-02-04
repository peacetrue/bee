package com.github.peacetrue.bee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author peace
 **/
@Configuration(proxyBeanMethods = false)
public class BeeMainConfiguration {

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

    /**
     * 开发环境允许跨域。
     *
     * @return 跨域配置
     */
    @Bean
    @Profile("dev")
    public WebMvcConfigurer corsWebMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

}
