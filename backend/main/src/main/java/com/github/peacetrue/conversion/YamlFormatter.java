package com.github.peacetrue.conversion;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.github.peacetrue.beanmap.BeanMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * YAML 格式化器。
 *
 * @author peace
 **/
@Slf4j
public class YamlFormatter implements Formatter<BeanMap> {

    public static final String FORMAT = "yaml";

    private YAMLMapper yamlMapper;

    @Override
    public BeanMap parse(String text, @Nullable String options) throws IOException {
        return yamlMapper.readValue(text, BeanMap.class);
    }

    @Override
    public String print(BeanMap object, @Nullable String options) throws IOException {
        return yamlMapper.writeValueAsString(object);
    }

    @Autowired
    public void setYamlMapper(YAMLMapper yamlMapper) {
        this.yamlMapper = yamlMapper;
    }

    @Autowired
    public void setFormatterRegistrar(FormatterRegistrar formatterRegistrar) {
        formatterRegistrar.registerFormatter(FORMAT, this);
    }
}
