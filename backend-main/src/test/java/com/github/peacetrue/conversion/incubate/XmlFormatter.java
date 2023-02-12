package com.github.peacetrue.conversion.incubate;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.peacetrue.beanmap.BeanMap;
import com.github.peacetrue.conversion.Formatter;
import com.github.peacetrue.conversion.FormatterRegistrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * XML 格式化器。
 *
 * @author peace
 **/
@Slf4j
public class XmlFormatter implements Formatter<BeanMap> {

    /** 数据格式 */
    public static final String FORMAT = "xml";

    private XmlMapper xmlMapper;

    @Override
    public BeanMap parse(String text, @Nullable String options) throws IOException {
        return xmlMapper.readValue(text, BeanMap.class);
    }

    @Override
    public String print(BeanMap object, @Nullable String options) throws IOException {
        return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    @Autowired
    public void setXmlMapper(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    @Autowired
    public void setFormatterRegistrar(FormatterRegistrar formatterRegistrar) {
        formatterRegistrar.registerFormatter(FORMAT, this);
    }
}
