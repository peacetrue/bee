package com.github.peacetrue.bee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}
