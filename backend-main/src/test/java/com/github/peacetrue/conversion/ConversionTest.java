package com.github.peacetrue.conversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.github.peacetrue.beanmap.BeanMap;
import com.github.peacetrue.beanmap.BeanMapUtils;
import com.github.peacetrue.net.URLQueryUtils;
import com.github.peacetrue.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapperImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.github.peacetrue.test.SourcePathUtils.getTestResourceAbsolutePath;

/**
 * @author peace
 **/
@Slf4j
public class ConversionTest {

    public static final User USER = User.builder()
            .name("张三")
            .age(18)
            .roles(Arrays.asList(new Role("admin", "管理员"), new Role("employee", "员工")))
            .tags(Arrays.asList("goodness", "tall"))
            .build();

    public static final BeanMap USER_MAP = new ObjectMapper().convertValue(USER, BeanMap.class);

    @Test
    void json() throws IOException {
        log.info("user: {}", USER);
        ObjectMapper objectMapper = new ObjectMapper();
        Path path = Paths.get(getTestResourceAbsolutePath("/conversion/user.json"));
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(
                Files.newBufferedWriter(path), USER
        );
        User user = objectMapper.readValue(Files.newBufferedReader(path), User.class);
        Assertions.assertEquals(USER, user);
    }

    @Test
    void xml() throws IOException {
        log.info("user: {}", USER);
        XmlMapper objectMapper = new XmlMapper();
        objectMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
        Path path = Paths.get(getTestResourceAbsolutePath("/conversion/user.xml"));
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(
                Files.newBufferedWriter(path), USER
        );
        User user = objectMapper.readValue(Files.newBufferedReader(path), User.class);
        Assertions.assertEquals(USER, user);
    }

    public static final JavaPropsSchema SCHEMA = JavaPropsSchema.emptySchema()
            // Use brackets instead of dots for array index
            .withWriteIndexUsingMarkers(true)
            .withFirstArrayOffset(0);

    /**
     * @see <a href="https://github.com/FasterXML/jackson-dataformats-text/issues/245">How to convert POJO to properties using Charset.UTF8 #245</a>
     * @see <a href="https://stackoverflow.com/questions/10008989/library-for-converting-native2ascii-and-vice-versa">Library for converting native2ascii and vice versa</a>
     */
    @Test
    void properties() throws IOException {
        log.info("user: {}", USER);
        JavaPropsMapper objectMapper = new JavaPropsMapper();
        Path path = Paths.get(getTestResourceAbsolutePath("/conversion/user.properties"));
        objectMapper.writer(SCHEMA).writeValue(
                Files.newBufferedWriter(path, StandardCharsets.UTF_8), USER
        );
        User user = objectMapper.readValue(Files.newBufferedReader(path, StandardCharsets.UTF_8), User.class);
        Assertions.assertEquals(USER, user);

        // 非 ASCII 码，使用 \\uxx 表示，做解码操作
        String value = objectMapper.writer(SCHEMA).writeValueAsString(USER);
        Files.write(path, StringEscapeUtils.unescapeJava(value).getBytes(StandardCharsets.UTF_8));
        user = objectMapper.readValue(Files.newBufferedReader(path, StandardCharsets.UTF_8), User.class);
        Assertions.assertEquals(USER, user);
    }

    @Test
    void yaml() throws IOException {
        log.info("user: {}", USER);
        YAMLMapper objectMapper = new YAMLMapper();
        objectMapper.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        Path path = Paths.get(getTestResourceAbsolutePath("/conversion/user.yaml"));
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(
                Files.newBufferedWriter(path), USER
        );
        User user = objectMapper.readValue(Files.newBufferedReader(path), User.class);
        Assertions.assertEquals(USER, user);
    }

    @Test
    void tieredBeanMap() {
        log.info("user: {}", USER);
        ObjectMapper objectMapper = new ObjectMapper();
        BeanMap tieredBeanMap = objectMapper.convertValue(USER, BeanMap.class);
        log.info("beanMap: {}", tieredBeanMap);
        User user = objectMapper.convertValue(tieredBeanMap, User.class);
        Assertions.assertEquals(USER, user);
    }

    @Test
    void flattedBeanMap() throws IOException {
        log.info("user: {}", USER);
        JavaPropsMapper objectMapper = new JavaPropsMapper();
        Map<String, String> tieredBeanMap = objectMapper.writeValueAsMap(USER, SCHEMA);
        log.info("beanMap: {}", tieredBeanMap);
        User user = objectMapper.readMapAs(tieredBeanMap, User.class);
        Assertions.assertEquals(USER, user);
    }

    /** URL 字符转义，重复属性，与属性文件还是有区别 */
    @Test
    void urlNotSupport() throws IOException {
        log.info("user: {}", USER);
        // Use brackets instead of dots for array index
        JavaPropsSchema schema = JavaPropsSchema.emptySchema()
                .withLineEnding("&")
                .withWriteIndexUsingMarkers(true);
        JavaPropsMapper objectMapper = new JavaPropsMapper();
        String value = objectMapper.writer(schema).writeValueAsString(USER);
        value = StringEscapeUtils.unescapeJava(value);
        log.info("value: {}", value);
        User user = objectMapper.reader(schema).readValue(value, User.class);
        Assertions.assertNotEquals(USER, user);
    }

    /** 使用原生的转换方式。数组表达上存在差异。 */
    @Test
    void urlIncreaseNative() throws IOException {
        log.info("user: {}", USER);
        JavaPropsMapper objectMapper = new JavaPropsMapper();
        Map<String, String> beanMap = objectMapper.writeValueAsMap(USER, SCHEMA);
        log.info("beanMap: {}", beanMap);
        Map<String, List<String>> queryParams = URLQueryUtils.fromBeanMap(beanMap);
        log.info("queryParams: {}", queryParams);
        String query = URLQueryUtils.toQuery(queryParams);
        log.info("query: {}", query);
        queryParams = URLQueryUtils.parseQuery(query);
        Map<String, Object> stringObjectMap = URLQueryUtils.toBeanMap(queryParams);
        User user = objectMapper.readMapAs(((Map) stringObjectMap), SCHEMA, User.class);
        Assertions.assertEquals(USER, user);

        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(new User());
        beanWrapper.setAutoGrowNestedPaths(true);
        beanWrapper.setAutoGrowCollectionLimit(100);
        beanWrapper.setPropertyValues(stringObjectMap);
        Assertions.assertEquals(USER, beanWrapper.getRootInstance());
    }

    /** 使用自定义的转换方式。数据类型转换上存在不足 */
    @Test
    void urlIncreaseCustom() {
        log.info("user: {}", USER);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> beanMap = objectMapper.convertValue(USER, BeanMap.class);
        Map<String, List<String>> queryParams = URLQueryUtils.flatten(beanMap);
        String query = URLQueryUtils.toQuery(queryParams);
        log.info("value: {}", query);
        queryParams = URLQueryUtils.parseQuery(query);
        beanMap = URLQueryUtils.toBeanMap(queryParams);
        beanMap = BeanMapUtils.tier(beanMap);
        User user = objectMapper.convertValue(beanMap, User.class);
        Assertions.assertEquals(USER, user);
    }

}
