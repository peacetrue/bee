package com.github.peacetrue.conversion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * 文本数据互转控制器。
 *
 * @author peace
 **/
@Slf4j
@ResponseBody
@RequestMapping("/conversion")
public class ConversionController {

    @Autowired
    private FormatterRegistrar formatterRegistrar;
    @Autowired
    private ConversionService conversionService;

    /**
     * 获取支持的数据格式。
     *
     * @return 支持的数据格式
     */
    @GetMapping("/formats")
    public List<String> getFormats() {
        return formatterRegistrar.getFormats();
    }

    /**
     * 转换参数，用于支持 Spring 参数注入，内部使用。
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Args {
        private String sourceData;
        private FormatWrapper sourceFormat;
        private FormatWrapper targetFormat;
    }

    /**
     * 同 {@link ConversionService#convert(String, FormatWrapper, FormatWrapper)}。
     *
     * @param args 转换参数
     * @return 目标文本数据
     * @throws IOException 转换过程中发生异常
     */
    @PostMapping
    public String convert(Args args) throws IOException {
        return conversionService.convert(
                args.getSourceData(),
                args.getSourceFormat(),
                args.getTargetFormat()
        );
    }


}
