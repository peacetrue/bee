package com.github.peacetrue.conversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.github.peacetrue.net.URLQueryUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.InputStream;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 转换控制器配置。
 *
 * @author peace
 **/
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        JacksonAutoConfiguration.class,
        ConversionAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        MockMvcAutoConfiguration.class,
})
class ConversionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JavaPropsMapper javaPropsMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getFormats() throws Exception {
        this.mockMvc.perform(get("/conversion/formats")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
        ;
    }

    @Test
    void convert() throws Exception {
        InputStream sourceStream = getClass().getResourceAsStream("/conversion/user.json");
        String sourceData = IOUtils.toString(Objects.requireNonNull(sourceStream));
        InputStream targetStream = getClass().getResourceAsStream("/conversion/user.yaml");
        String targetData = IOUtils.toString(Objects.requireNonNull(targetStream));
        targetData = StringUtils.removeEnd(targetData, "\n");

        DataConversion args = new DataConversion(
                new DataWrapper(JsonFormatter.FORMAT, sourceData),
                new DataWrapper(YamlFormatter.FORMAT, "")
        );
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(
//                URLQueryUtils.fromBeanMap(javaPropsMapper.writeValueAsMap(args))
//        );
        String params = objectMapper.writeValueAsString(args);
        log.debug("params: {}", params);
        this.mockMvc.perform(post("/conversion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(params)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(targetData))
        ;
    }
}
