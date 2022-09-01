package com.github.peacetrue.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器。
 *
 * @author peace
 **/
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 可指定耗时的回声服务。
     * 初衷用于测试负载时，触发连接超时；
     * 访问此接口的连接长时间不释放，导致连接数达到上限。
     *
     * @param input       输入参数
     * @param elapsedTime 耗时（毫秒）
     * @return 与输入参数相同
     * @throws InterruptedException 睡眠时被打断
     */
    @GetMapping("/echo")
    public String echo(@RequestParam(defaultValue = "") String input,
                       @RequestParam(defaultValue = "0") Integer elapsedTime) throws InterruptedException {
        if (elapsedTime > 0) Thread.sleep(elapsedTime);
        return input;
    }
}
