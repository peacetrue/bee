package com.github.peacetrue.bee;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * Bee 监控应用。
 *
 * @author peace
 **/
@EnableAdminServer
@SpringBootApplication
public class BeeMonitorApplication {

    /**
     * 启用应用。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(BeeMonitorApplication.class, args);
    }
}
