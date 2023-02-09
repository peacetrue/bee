package com.github.peacetrue.bee;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * OpenAPI 配置。
 *
 * @author peace
 **/
@Configuration
public class OpenAPIConfiguration {

    public static final String V1 = "application/vnd.bee.v1+json";

    @Value("${springdoc.api.info.version}")
    private String infoVersion;
    @Value("${spring.boot.admin.client.instance.service-base-url}")
    private String url;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("接口文档")
//                        .description("")
                        .version(infoVersion)
                        .license(new License().name("Apache 2.0").url("https://github.com/peacetrue/bee/blob/master/LICENSE")))
                .addServersItem(new Server().url(url).description("API 地址"))
                .externalDocs(new ExternalDocumentation()
                        .description("Bee 项目文档")
                        .url("https://peacetrue.github.io/bee"))
                ;
    }

    @Bean
    public WebMvcConfigurer apiVersionWebMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                configurer.defaultContentType(
                        MediaType.valueOf(V1),
                        // 兼容 openApi /v3/api-docs
                        MediaType.ALL
                );
            }
        };
    }

}
