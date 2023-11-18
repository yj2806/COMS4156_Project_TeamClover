package com.example.webservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WebServiceApplicationTests {

    @Value("${spring.datasource.username}")
    String username;
    @Test
    void contextLoads() {
        assertEquals("admin", username);
    }

}
