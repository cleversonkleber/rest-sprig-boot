package com.cleverson.rest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.BDDMockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cleverson.rest.exceptions.handler.ResourceNotFoundException;
import com.cleverson.rest.model.Person;
import com.cleverson.rest.repositories.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {


    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService service;

    private Person person0;

    @BeforeEach
    public void setup(){
        person0 = new Person("Cleverson", "Silva", "a@a.com", "male");
    }


    @DisplayName("Junit test for Given Person Object When Save Person then Return Person Object")
    @Test
    void testGivenPersonObject_wheSavePerson_thenReturnPersonObject(){
        //Given / Arrange
        given(personRepository.findByAddress(anyString())).willReturn(Optional.empty());
        given(personRepository.save(person0)).willReturn(person0);
        // When / Act
        Person savPerson = service.create(person0);
        // Then / Assert
        assertNotNull(savPerson);
        assertEquals("Cleverson", savPerson.getFirstName());

    }

    @DisplayName("Junit test for Given Existing Email When Save Person then threws Exception")
    @Test
    void testGivenExistingEmail_wheSavePerson_thenThrowsException(){
        //Given / Arrange
        given(personRepository.findByAddress(anyString())).willReturn(Optional.of(person0));
        
        // When / Act
        assertThrows(ResourceNotFoundException.class,()->{
            service.create(person0);
        });
        
        // Then / Assert
       verify(personRepository, never()).save(any(Person.class));

    }

    @DisplayName("Junit test for Given Find All Persons list When Return Persons list")
    @Test
    void testGivenPersonList_whenFindAllPersons_thenReturnPersonsList(){
        //Given / Arrange
        Person person1 = new Person("Jose", "Antonio", "ja@ja.com", "male");
         
        given(personRepository.findAll()).willReturn(List.of(person0,person1));
        
        // When / Act
        List<Person> persons =  service.findAll();
        
        // Then / Assert
        assertNotNull(persons);
        assertEquals(2, persons.size());

    }

    @DisplayName("Junit test for Given Empty Persons list When Return Persons list")
    @Test
    void testGivenPersonList_whenFindAllPersons_thenReturnEmptyPersonsList(){
        //Given / Arrange
        given(personRepository.findAll()).willReturn(Collections.emptyList());
        
        // When / Act
        List<Person> persons =  service.findAll();
        
        // Then / Assert
        assertTrue(persons.isEmpty());
        assertEquals(0, persons.size());

    }

    @DisplayName("Junit test for Given PersonId when findById then Return Persons Object")
    @Test
    void testGivenPersonId_whenFindByIdPerson_thenReturnEmptyPersonObject(){
        //Given / Arrange
        
        given(personRepository.findById(anyLong())).willReturn(Optional.of(person0));
        
        // When / Act
        Person personId =  service.findById(1L);
        
        // Then / Assert
        assertNotNull(personId);
        assertEquals("Cleverson", personId.getFirstName());

    }

    @DisplayName("Junit test for Given Update Person when Update then Return Update Persons Object")
    @Test
    void testGivenPersonobject_whenupdatePerson_thenReturnUpdatePersonObject(){
        //Given / Arrange
        person0.setId(1L);
        given(personRepository.findById(anyLong())).willReturn(Optional.of(person0));
        person0.setAddress("leandro@leandro.com");
        person0.setFirstName("Leandro");
        
        given(personRepository.save(person0)).willReturn(person0);

        // When / Act
        Person updateperson =  service.update(person0);
        
        // Then / Assert
        assertNotNull(updateperson);
        assertEquals("Leandro", updateperson.getFirstName());
        assertEquals("leandro@leandro.com", updateperson.getAddress());

    }

    @DisplayName("Junit test for Given PersonId when Delete then do Nothing")
    @Test
    void testGivenPersonobject_whenDeletePerson_thenDoNothing(){
        //Given / Arrange
        person0.setId(1L);
        given(personRepository.findById(anyLong())).willReturn(Optional.of(person0));
        willDoNothing().given(personRepository).delete(person0);

        // When / Act
        service.delete(person0.getId());
        
        // Then / Assert
        verify(personRepository, times(1)).delete(person0);

    }



}
