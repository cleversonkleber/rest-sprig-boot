package com.cleverson.rest.integrationstests.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cleverson.rest.config.TestConfigs;
import com.cleverson.rest.integrationstests.testcontainers.AbstractIntegrationTest;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {  

    public SwaggerIntegrationTest(){
        
    }

    @DisplayName("Junit test for Should Display Swagger UI Page")
    @Test
    public void testShouldDisplaySwaggerUIPage(){
        var content = given()
            .basePath("/swagger-ui/index.html")
            .port(TestConfigs.SERVER_PORT)
            .when()
                .get()
            .then()
                .statusCode(200)
            .extract()
                .body()
                    .asString();
        assertTrue(content.contains("Swagger UI"));


    }
}
