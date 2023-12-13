package com.example.webservice;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.springframework.http.HttpStatus.OK;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class APITests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup() {
        enableRestAssured();
    }

    @AfterEach
    void after() {
        RestAssuredMockMvc.reset();
    }

    private void enableRestAssured() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    @DisplayName("GET /facility")
    void getMessage() throws Exception {
        given()
                .when()
                .get("/facility")
                .then()
                .status(OK);
    }

}
