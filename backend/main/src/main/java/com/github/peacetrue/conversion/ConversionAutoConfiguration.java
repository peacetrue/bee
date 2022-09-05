package com.github.peacetrue.conversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.Generated;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文本数据互转自动配置。
 *
 * @author peace
 **/

@Configuration
@AutoConfigureAfter(JacksonAutoConfiguration.class)
public class ConversionAutoConfiguration {

    @Bean
    public ConversionController conversionController() {
        return new ConversionController();
    }

    @Bean
    
    public ConversionServiceImpl conversionService() {
        return new ConversionServiceImpl();
    }

    @Bean
    
    public JsonFormatter jsonFormatter() {
        return new JsonFormatter();
    }

    @Bean
    
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

//    @Bean
    public XmlFormatter xmlFormatter() {
        return new XmlFormatter();
    }

    @Bean
    
    @ConditionalOnMissingBean
    public XmlMapper xmlMapper() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        return xmlMapper;
    }

    @Bean
    
    public YamlFormatter yamlFormatter() {
        return new YamlFormatter();
    }

    @Bean
    
    @ConditionalOnMissingBean
    public YAMLMapper yamlMapper() {
        YAMLMapper yamlMapper = new YAMLMapper();
        yamlMapper.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        return yamlMapper;
    }

    @Bean
    
    public PropertiesFormatter propertiesFormatter() {
        return new PropertiesFormatter();
    }

    @Bean
    
    @ConditionalOnMissingBean
    public JavaPropsMapper javaPropsMapper() {
        return new JavaPropsMapper();
    }


}
