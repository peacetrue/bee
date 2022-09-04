package com.github.peacetrue.conversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.net.URLQueryUtils;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author peace
 **/
@Slf4j
public class CollectionConverter implements Formatter<List<String>> {

    private ObjectMapper objectMapper;

    private Map<String, Object> resolveOptions(@Nullable String options) {
        return StringUtils.hasText(options)
                ? URLQueryUtils.toBeanMap(URLQueryUtils.parseQuery(options))
                : Collections.emptyMap();
    }

    @Override
    public List<String> parse(String text, @Nullable String options) throws IOException {
        //  delimiter=%0D%0A
        log.info("convert data {} with options '{}'", text, options);
        Map<String, Object> optionsMap = resolveOptions(options);
        log.debug("got optionsMap: {}", optionsMap);
        String delimiter = (String) optionsMap.getOrDefault("delimiter", "\n");
        log.debug("got delimiter: {}", delimiter);
        return Pattern.compile(delimiter).splitAsStream(text).collect(Collectors.toList());
    }

    @Override
    public String print(List<String> object, @Nullable String options) throws IOException {
        log.info("convert bridge {} with options '{}'", object, options);
        Map<String, Object> optionsMap = resolveOptions(options);
        log.debug("got optionsMap: {}", optionsMap);
        //  delimiter=%0D%0A&prefix=&suffix=
        return object.stream().collect(Collectors.joining(
                (String) optionsMap.getOrDefault("delimiter", "\",\""),
                (String) optionsMap.getOrDefault("prefix", "\""),
                (String) optionsMap.getOrDefault("suffix", "\"")
        ));
    }


    @Autowired
    @Generated
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    @Generated
    public void setFormatterRegistrar(FormatterRegistrar formatterRegistrar) {
        formatterRegistrar.registerFormatter("collection", this);
    }

}
