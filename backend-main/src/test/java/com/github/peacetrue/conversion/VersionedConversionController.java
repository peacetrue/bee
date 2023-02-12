package com.github.peacetrue.conversion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author peace
 **/
@Slf4j
@ResponseBody
@RequestMapping("/conversion")
public class VersionedConversionController {

    @Autowired
    private FormatterRegistrar formatterRegistrar;

    /**
     * 获取系统支持转换的数据格式。
     *
     * @return 支持转换的数据格式
     */
    @GetMapping(value = "/formats", produces = "application/vnd.bee.v2+json")
    public List<String> getFormatsV2() {
        List<String> formats = new ArrayList<>(formatterRegistrar.getFormats());
        formats.add("v2");
        return formats;
    }

    /**
     * 获取系统支持转换的数据格式。
     *
     * @return 支持转换的数据格式
     */
    @GetMapping(value = "/formats", produces = "application/vnd.bee.v3+json")
    public List<String> getFormatsV3() {
        List<String> formats = formatterRegistrar.getFormats();
        formats.add("v3");
        return formats;
    }

}
