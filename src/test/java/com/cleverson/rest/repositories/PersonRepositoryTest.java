package com.cleverson.rest.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cleverson.rest.model.Person;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    private Person person0;

    @BeforeEach
    public void setup(){
        person0 = new Person("Cleverson", "Silva", "a@a.com", "male");
    }

   
    @DisplayName("Display Name")
    @Test
    void testGivenPersonObject_wheSave_thenReturnSavePerson(){
        //Given / Arrange
        // When / Act
        // Then / Assert

         //Given / Arrange

        // When / Act
        Person savePerson = personRepository.save(person0);

        // Then / Assert
        assertNotNull(savePerson);
        assertTrue(savePerson.getId() > 0);

    }

    @DisplayName("Display list person")
    @Test
    void testGivenPersonList_whenFindAll_thenReturnPersonList(){
        //Given / Arrange
        // When / Act
        // Then / Assert

         //Given / Arrange

        Person person1 = new Person("Leonardo", "Silva", "a@a.com", "male");
        personRepository.save(person0);
        personRepository.save(person1);
        // When / Act
        List<Person> personList = personRepository.findAll();

        // Then / Assert
        assertNotNull(personList);
        assertEquals(2, personList.size());

    }
    @DisplayName("Display list person")
    @Test
    void testGivenPersonObject_whenFindById_thenReturnPersonObject(){
        //Given / Arrange
        // When / Act
        // Then / Assert

         //Given / Arrange

        personRepository.save(person0);
        // When / Act
        Person savePerson = personRepository.findById(person0.getId()).get();

        // Then / Assert
        assertNotNull(savePerson);
        assertEquals(person0.getId(), savePerson.getId());

    }

    @DisplayName("Given Person Object when findByEmail then Return Person Object")
    @Test
    void testGivenPersonObject_whenFindByAdrreess_thenReturnPersonObject(){
        //Given / Arrange
        // When / Act
        // Then / Assert

         //Given / Arrange
        personRepository.save(person0);
        // When / Act
        Person savePerson = personRepository.findByAddress(person0.getAddress()).get();

        // Then / Assert
        assertNotNull(savePerson);
        assertEquals(person0.getId(), savePerson.getId());

    }

    @DisplayName("Given Person Object when Update then Return Update Person Object")
    @Test
    void testGivenPersonObject_whenUpdatePerson_thenReturnPersonObject(){
        //Given / Arrange
        // When / Act
        // Then / Assert

         //Given / Arrange

        personRepository.save(person0);
        // When / Act
        Person savePerson = personRepository.findByAddress(person0.getAddress()).get();
        savePerson.setFirstName("João");
        savePerson.setAddress("joao@j.com");
        
        Person updatePerson = personRepository.save(savePerson);
        // Then / Assert
        assertNotNull(updatePerson);
        assertEquals("João", savePerson.getFirstName());
        assertEquals("joao@j.com", savePerson.getAddress());

    }

    @DisplayName("Given Person Object when Delete then Return remove Person")
    @Test
    void testGivenPersonObject_whenDeletePerson_thenRemovePerson(){
        //Given / Arrange
        // When / Act
        // Then / Assert

         //Given / Arrange

        personRepository.save(person0);
        // When / Act
        personRepository.deleteById(person0.getId());
        Optional<Person> personDelete = personRepository.findById(person0.getId());
        // Then / Assert
        assertTrue(personDelete.isEmpty());


    }


    @DisplayName("Given Person Object when JPQL then Return person Person")
    @Test
    void testGivenPersonObject_whenFindJPQL_thenReturnPersonObject(){
        //Given / Arrange
        // When / Act
        // Then / Assert

         //Given / Arrange

        personRepository.save(person0);
        // When / Act
        Person savePerson = personRepository.findByJPQL("Cleverson", "Silva");

        // Then / Assert
        assertNotNull(savePerson);
        assertEquals("Cleverson", savePerson.getFirstName());
        assertEquals("Silva", savePerson.getLastName());

    }


    @DisplayName("Given Person Object when JPQLParameters then Return person Person")
    @Test
    void testGivenPersonObject_whenFindJPQLParameter_thenReturnPersonObject(){
        //Given / Arrange
        // When / Act
        // Then / Assert

         //Given / Arrange

        personRepository.save(person0);
        // When / Act
        Person savePerson = personRepository.findByJPQLNamedParameters("Cleverson", "Silva");

        // Then / Assert
        assertNotNull(savePerson);
        assertEquals("Cleverson", savePerson.getFirstName());
        assertEquals("Silva", savePerson.getLastName());

    }
    @DisplayName("Given Person Object when JPQLParameters then Return person Person")
    @Test
    void testGivenPersonObject_whenFindNativeSQL_thenReturnPersonObject(){
        //Given / Arrange
        // When / Act
        // Then / Assert

         //Given / Arrange

        personRepository.save(person0);
        // When / Act
        Person savePerson = personRepository.findByNativeSQL("Cleverson", "Silva");

        // Then / Assert
        assertNotNull(savePerson);
        assertEquals("Cleverson", savePerson.getFirstName());
        assertEquals("Silva", savePerson.getLastName());

    }

    @DisplayName("Given Person Object when Native sql Parameters then Return person Person")
    @Test
    void testGivenPersonObject_whenFindNativeSQLNameParameters_thenReturnPersonObject(){
        //Given / Arrange
        // When / Act
        // Then / Assert

         //Given / Arrange

        personRepository.save(person0);
        // When / Act
        Person savePerson = personRepository.findBySQPNativeNamedParameters("Cleverson", "Silva");

        // Then / Assert
        assertNotNull(savePerson);
        assertEquals("Cleverson", savePerson.getFirstName());
        assertEquals("Silva", savePerson.getLastName());

    }


}
