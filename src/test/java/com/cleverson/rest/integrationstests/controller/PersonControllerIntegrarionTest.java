package com.cleverson.rest.integrationstests.controller;


import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cleverson.rest.config.TestConfigs;
import com.cleverson.rest.integrationstests.testcontainers.AbstractIntegrationTest;
import com.cleverson.rest.model.Person;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PersonControllerIntegrarionTest extends AbstractIntegrationTest{
   private static RequestSpecification specification;
   private static ObjectMapper objectMapper;
   private static Person person; 

   @BeforeAll
   public static void setup(){
      objectMapper = new ObjectMapper();
      objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      specification = new RequestSpecBuilder()
                      .setBasePath("/person")
                      .setPort(TestConfigs.SERVER_PORT)
                           .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                           .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                     .build();
        person = new Person("Cleverson", "Silva", "a@a.com", "male");
   }
   
   @Test
   @Order(1)
   @DisplayName("Junit integration for Given Create Person Object When Should Person then Return Person Object")
   void integrationTestGivenPersonObject_when_CreatePerson_ShouldReturnAPersonObject() throws JsonMappingException, JsonProcessingException {
       var content = given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(person)
            .when()
                .post()
            .then()
                .statusCode(200)
            .extract()
                .body()
                    .asString();
      Person createdPerson = objectMapper.readValue(content, Person.class);
      person =  createdPerson;
      assertNotNull(createdPerson);
      assertNotNull(createdPerson.getFirstName());
      assertNotNull(createdPerson.getLastName());
      assertNotNull(createdPerson.getAddress());
      assertNotNull(createdPerson.getGender());

      assertTrue(createdPerson.getId() > 0);
      assertEquals("Cleverson",createdPerson.getFirstName());
      assertEquals("Silva", createdPerson.getLastName());
      assertEquals("a@a.com", createdPerson.getAddress());
      assertEquals("male",createdPerson.getGender());



    }

}
