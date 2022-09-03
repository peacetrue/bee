package com.github.peacetrue.conversion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     * 同 {@link ConversionService#convert(String, FormatWrapper, FormatWrapper)}。
     *
     * @param conversion 转换参数
     * @return 目标文本数据
     * @throws IOException 转换过程中发生读写异常
     */
    @PostMapping
    public String convert(@Valid @RequestBody DataConversion conversion) throws IOException {
        return conversionService.convert(
                conversion.getSource().getContent(),
                new FormatWrapper(conversion.getSource().getFormat(), null),
                new FormatWrapper(conversion.getTarget().getFormat(), null)
        );
    }


}
