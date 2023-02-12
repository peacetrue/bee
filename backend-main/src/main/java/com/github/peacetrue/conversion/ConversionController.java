package com.github.peacetrue.conversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据转换。
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
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 获取系统支持转换的数据格式。
     *
     * @return 支持转换的数据格式
     */
    @GetMapping(value = "/formats")
    public List<String> getFormats() {
        return formatterRegistrar.getFormats();
    }

    /**
     * 转换指定格式的原始数据内容为其他格式的目标数据内容。
     *
     * @param conversion 数据转换参数
     * @return 目标数据内容
     * @throws IOException 转换过程中发生读写异常
     */
    @PostMapping
    public String convert(@Valid @RequestBody DataConversion conversion) throws IOException {
        // 返回字符串会被 StringHttpMessageConverter 处理，这里手动转换为 JSON 格式
        return objectMapper.writeValueAsString(
                conversionService.convert(
                        conversion.getSource().getContent(),
                        new FormatWrapper(conversion.getSource().getFormat(), null),
                        new FormatWrapper(conversion.getTarget().getFormat(), null)
                )
        );
    }

}
