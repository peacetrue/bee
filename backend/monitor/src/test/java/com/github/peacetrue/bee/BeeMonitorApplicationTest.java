package com.github.peacetrue.bee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author peace
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest
class BeeMonitorApplicationTest {

    @Autowired
    private BeeMonitorApplication application;

    @Test
    void basic() {
        Assertions.assertNotNull(application);
    }
}
