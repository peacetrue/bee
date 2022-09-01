package com.github.peacetrue.conversion;

import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsSchema;
import com.github.peacetrue.beanmap.BeanMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * YAML 格式化器。
 *
 * @author peace
 **/
@Slf4j
public class PropertiesFormatter implements Formatter<BeanMap> {

    /** 数据格式 */
    public static final String FORMAT = "properties";
    private static final JavaPropsSchema SCHEMA = JavaPropsSchema.emptySchema()
            // Use brackets instead of dots for array index
            .withWriteIndexUsingMarkers(true)
            .withFirstArrayOffset(0);


    private JavaPropsMapper javaPropsMapper;

    @Override
    public BeanMap parse(String text, @Nullable String options) throws IOException {
        return javaPropsMapper.reader(SCHEMA).readValue(text, BeanMap.class);
    }

    @Override
    public String print(BeanMap object, @Nullable String options) throws IOException {
        String text = javaPropsMapper.writer(SCHEMA).writeValueAsString(object);
        return StringEscapeUtils.unescapeJava(text);
    }

    @Autowired
    public void setJavaPropsMapper(JavaPropsMapper javaPropsMapper) {
        this.javaPropsMapper = javaPropsMapper;
    }

    @Autowired
    public void setFormatterRegistrar(FormatterRegistrar formatterRegistrar) {
        formatterRegistrar.registerFormatter(FORMAT, this);
    }
}
