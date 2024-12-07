package com.example.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        assertNotNull(context);
        int count = context.getBeanDefinitionCount();
        System.out.println("Number of beans " + count);
        for (var v : context.getBeanDefinitionNames()) {
            System.out.println(v);
        }
    }

    @Test @Disabled
    void getBean() {
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> context.getBean(RestTemplate.class));
    }

}
