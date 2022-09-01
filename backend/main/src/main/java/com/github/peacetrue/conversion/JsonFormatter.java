package com.github.peacetrue.conversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.beanmap.BeanMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * JSON 格式化器。
 *
 * @author peace
 **/
@Slf4j
public class JsonFormatter implements Formatter<BeanMap> {

    public static final String FORMAT = "json";

    private ObjectMapper objectMapper;

    @Override
    public BeanMap parse(String text, @Nullable String options) throws IOException {
        return objectMapper.readValue(text, BeanMap.class);
    }

    @Override
    public String print(BeanMap object, @Nullable String options) throws IOException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setFormatterRegistrar(FormatterRegistrar formatterRegistrar) {
        formatterRegistrar.registerFormatter(FORMAT, this);
    }
}
