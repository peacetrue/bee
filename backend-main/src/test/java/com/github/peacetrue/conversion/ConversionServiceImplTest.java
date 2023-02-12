package com.github.peacetrue.conversion;

import com.github.peacetrue.conversion.incubate.RawConverter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author peace
 **/
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        JacksonAutoConfiguration.class,
        ConversionAutoConfiguration.class,
})
class ConversionServiceImplTest {

    @Autowired
    private ConversionServiceImpl conversionService;

    /** 测试未定义的数据类型异常 */
    @Test
    void convert() {
        FormatWrapper undefined = new FormatWrapper("not exists format", null);
        FormatWrapper json = new FormatWrapper(YamlFormatter.FORMAT, null);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            conversionService.convert("{}", undefined, json);
        });

        Assertions.assertThrows(IllegalStateException.class, () -> {
            conversionService.convert("{}", json, undefined);
        });

        FormatWrapper raw = new FormatWrapper("raw", null);
        conversionService.registerFormatter(raw.getFormat(), new RawConverter());
        Assertions.assertThrows(IllegalStateException.class, () -> {
            conversionService.convert("{}", raw, json);
        });
    }
}
