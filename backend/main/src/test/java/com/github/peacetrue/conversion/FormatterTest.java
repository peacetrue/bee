package com.github.peacetrue.conversion;

import com.github.peacetrue.beanmap.BeanMap;
import com.github.peacetrue.beanmap.BeanMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

import static com.github.peacetrue.conversion.ConversionTest.USER_MAP;

/**
 * @author peace
 **/
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ConversionAutoConfiguration.class})
class FormatterTest {

    @Autowired
    private List<Formatter<?>> formatters;

    @Test
    @SuppressWarnings("unchecked")
    void basic() throws IOException {
        for (Formatter<?> formatter : formatters) {
            test((Formatter<BeanMap>) formatter);
        }
    }

    private void test(Formatter<BeanMap> formatter) throws IOException {
        log.info("test {}", formatter);
        String text = formatter.print(USER_MAP);
        log.info("text: {}", text);
        Assertions.assertEquals(
                new TreeMap<>(BeanMapUtils.flatten(USER_MAP)).toString(),
                new TreeMap<>(BeanMapUtils.flatten(formatter.parse(text))).toString()
        );
    }

}
