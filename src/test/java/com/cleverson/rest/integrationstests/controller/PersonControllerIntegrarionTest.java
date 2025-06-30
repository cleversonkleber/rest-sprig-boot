package com.cleverson.rest.integrationstests.controller;


import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;


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

   @Test
   @Order(2)
   @DisplayName("Junit integration for Given Update Person Object When Update one Person then Return Person Object")
   void integrationTestGivenPersonObject_when_UpdateOnePerson_ShouldReturnAUpdatePersonObject() throws JsonMappingException, JsonProcessingException {
      person.setFirstName("Jose");
      person.setAddress("jose@jose.com");
      var content = given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(person)
            .when()
                .put()
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
      assertEquals("Jose",createdPerson.getFirstName());
      assertEquals("Silva", createdPerson.getLastName());
      assertEquals("jose@jose.com", createdPerson.getAddress());
      assertEquals("male",createdPerson.getGender());



    }


   @Test
   @Order(3)
   @DisplayName("Junit integration for Given findById Person Object When findById then Return Person Object")
   void integrationTestGivenPersonObject_when_findById_ShouldReturnAPersonObject() throws JsonMappingException, JsonProcessingException {

      var content = given().spec(specification)
            .pathParam("id", person.getId())
            .when()
                .get("{id}")
            .then()
                .statusCode(200)
            .extract()
                .body()
                    .asString();
      Person createdPerson = objectMapper.readValue(content, Person.class);

      assertNotNull(createdPerson);
      assertNotNull(createdPerson.getFirstName());
      assertNotNull(createdPerson.getLastName());
      assertNotNull(createdPerson.getAddress());
      assertNotNull(createdPerson.getGender());

      assertTrue(createdPerson.getId() > 0);
      assertEquals("Jose",createdPerson.getFirstName());
      assertEquals("Silva", createdPerson.getLastName());
      assertEquals("jose@jose.com", createdPerson.getAddress());
      assertEquals("male",createdPerson.getGender());




    }

   @Test
   @Order(4)
   @DisplayName("Junit integration Given Person Object When findAll should then Return a PersonsList")
   void integrationTest_when_findAll_ShouldReturnAPersonList() throws JsonMappingException, JsonProcessingException {

      var anotherPerson = new Person("Gabriel", "Joaquim", "gab@gab.com", "male");

      given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(anotherPerson)
            .when()
                .post()
            .then()
                .statusCode(200);

      var content = given().spec(specification)
            .when()
                .get()
            .then()
                .statusCode(200)
            .extract()
                .body()
                    .asString();
      Person[] myPersons = objectMapper.readValue(content, Person[].class);
      List<Person> persons =  Arrays.asList(myPersons);
      
      Person foundPersonOne = persons.get(0);

      assertNotNull(foundPersonOne);
      assertNotNull(foundPersonOne.getFirstName());
      assertNotNull(foundPersonOne.getLastName());
      assertNotNull(foundPersonOne.getAddress());
      assertNotNull(foundPersonOne.getGender());

      assertTrue(foundPersonOne.getId() > 0);
      assertEquals("Jose",foundPersonOne.getFirstName());
      assertEquals("Silva", foundPersonOne.getLastName());
      assertEquals("jose@jose.com", foundPersonOne.getAddress());
      assertEquals("male",foundPersonOne.getGender());

      Person foundPersonTwo = persons.get(1);

      assertNotNull(foundPersonTwo);
      assertNotNull(foundPersonTwo.getFirstName());
      assertNotNull(foundPersonTwo.getLastName());
      assertNotNull(foundPersonTwo.getAddress());
      assertNotNull(foundPersonTwo.getGender());

      assertTrue(foundPersonTwo.getId() > 0);
      assertEquals("Gabriel",foundPersonTwo.getFirstName());
      assertEquals("Joaquim", foundPersonTwo.getLastName());
      assertEquals("gab@gab.com", foundPersonTwo.getAddress());
      assertEquals("male",foundPersonTwo.getGender());




    }


   @Test
   @Order(4)
   @DisplayName("Junit integration for Given delete Person Object When delete then person Return NoContent")
   void integrationTest_when_delete_ShouldReturnNoContent() throws JsonMappingException, JsonProcessingException {

      given().spec(specification)
            .pathParam("id", person.getId())
            .when()
                .delete("{id}")
            .then()
                .statusCode(204);

    }

}
