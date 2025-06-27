package com.cleverson.rest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.cleverson.rest.exceptions.handler.ResourceNotFoundException;
import com.cleverson.rest.model.Person;
import com.cleverson.rest.services.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class PersonControllerTest {
   
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PersonService personService;

    private Person person;

    @BeforeEach
    public void setup(){
        person = new Person("Cleverson", "Silva", "a@a.com", "male");
    }
    
    @DisplayName("Junit test for Given Person Object When Save Person then Return Save Person")
    @Test
    void testGivenPersonObject_wheCreatePerson_thenReturnSavedPerson() throws JsonProcessingException, Exception {
        //Given / Arrange
        given(personService.create(any(Person.class))).willAnswer((invocation)-> invocation.getArgument(0));

        // When / Act
        ResultActions response =  mockMvc.perform(post("/person")
                                   .contentType(MediaType.APPLICATION_JSON)
                                   .content(objectMapper.writeValueAsString(person)));


        // Then / Assert
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())));

    }

    @DisplayName("Junit test for Given Person Object When Save Person then Return Save Person")
    @Test
    void testGivenListOfPersons_whenAllPersons_thenReturnPersonList() throws JsonProcessingException, Exception {
        //Given / Arrange
        List<Person> persons =  new ArrayList<>();
        persons.add(person);
        persons.add(new Person("Jose", "Silva", "j@j.com", "male"));
        persons.add(new Person("Adao", "Camara", "a@ad.com", "male"));

        given(personService.findAll()).willReturn(persons);


        // When / Act
        ResultActions response =  mockMvc.perform(get("/person"));


        // Then / Assert
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(persons.size())));

    }

    @DisplayName("Junit test for Given PersonId Object When FindById Person then Return Person Object")
    @Test
    void testGivenPersonId_whenFindById_thenReturnPersonObject() throws JsonProcessingException, Exception {
        //Given / Arrange
        long personId = 1l;

        given(personService.findById(personId)).willReturn(person);

        // When / Act
        ResultActions response =  mockMvc.perform(get("/person/{id}", personId));


        // Then / Assert
        response
            .andExpect(status().isOk())
            .andDo(print())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.address", is(person.getAddress())))
                .andExpect(jsonPath("$.gender", is(person.getGender())));

    }

    @DisplayName("Junit test for Given PersonId Invalid When FindById Person then Return Not found")
    @Test
    void testGivenIvalidPersonId_whenFindById_thenReturnPersonNotfound() throws JsonProcessingException, Exception {
        //Given / Arrange
        long personId = 1l;

        given(personService.findById(personId)).willThrow(ResourceNotFoundException.class);

        // When / Act
        ResultActions response =  mockMvc.perform(get("/person/{id}", personId));


        // Then / Assert
        response
            .andExpect(status().isNotFound())
            .andDo(print());

    }

    @DisplayName("Junit test for Given Update Person When Update Person then Return Update Person")
    @Test
    void testGivenUpdatePerson_whenupdate_thenReturnPersonObject() throws JsonProcessingException, Exception {
        //Given / Arrange
        long personId = 1l;

        given(personService.findById(personId)).willReturn(person);
        given(personService.update(any(Person.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));;

        // When / Act
        Person updatePerson = new Person("Jose", "Silva", "j@j.com", "male");
        ResultActions response =  mockMvc.perform(put("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatePerson)));


        // Then / Assert
        response
            .andExpect(status().isOk())
            .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatePerson.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatePerson.getLastName())))
                .andExpect(jsonPath("$.address", is(updatePerson.getAddress())))
                .andExpect(jsonPath("$.gender", is(updatePerson.getGender())));

    }

    @DisplayName("Junit test for Given Unixistent Person When Update Person then Return Update Not Found")
    @Test
    void testGivenUnixistentPerson_whenUpdate_thenReturnNotFound() throws JsonProcessingException, Exception {
        //Given / Arrange
        long personId = 1l;

        given(personService.findById(personId)).willThrow(ResourceNotFoundException.class);
        given(personService.update(any(Person.class)))
                .willAnswer((invocation)-> invocation.getArgument(1));;

        // When / Act
        Person updatePerson = new Person("Jose", "Silva", "j@j.com", "male");
        ResultActions response =  mockMvc.perform(put("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatePerson)));


        // Then / Assert
        response
            .andExpect(status().isNotFound())
            .andDo(print());

    }

    @DisplayName("Junit test for Given PersonId Person When Delete Person then Return NotContent")
    @Test
    void testGivenPersonId_whendelete_thenReturnNotContent() throws JsonProcessingException, Exception {
        //Given / Arrange
        long personId = 1L;

        willDoNothing().given(personService).delete(personId);


        // When / Act

        ResultActions response =  mockMvc.perform(delete("/person/{id}", personId));


        // Then / Assert
        response
            .andExpect(status().isNoContent())
            .andDo(print());

    }



}
