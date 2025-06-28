package com.cleverson.rest.integrationstests.controller;

import org.springframework.boot.test.context.SpringBootTest;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PersonControllerIntegrarionTest extends AbstractIntegrationTest{
   private static RequestSpecification specification;
   private static ObjectMapper objectMapper;
   private static Person person; 



}
