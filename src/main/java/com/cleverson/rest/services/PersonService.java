package com.cleverson.rest.services;

import com.cleverson.rest.exceptions.handler.ResourceNotFoundException;
import com.cleverson.rest.model.Person;
import com.cleverson.rest.repositories.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository personRepository;


    public List<Person> findAll(){
        logger.info("finding all people!");
        List<Person> personList = personRepository.findAll();
        // for (int i = 0; i < 8;i++){
        //     Person person = mockPerson(i);
        //     personList.add(person);
        // }
        return personList;
    }

    public Person create(Person person){
        logger.info("Creating one person!");
        Optional<Person> savedPerson = personRepository.findByAddress(person.getAddress());
        if(savedPerson.isPresent()){
            throw new ResourceNotFoundException(
                "Person already exist with given e-mail:" + person.getAddress()
            );

        }
        
        personRepository.save(person);
        return person;
    }
    public Person update(Person person){
        logger.info("Update one person!");
        var entity = personRepository.findById(person.getId())
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return personRepository.save(person);
    }
    public void delete(Long id){
        logger.info("Deleting one person!");
        var entity = personRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("No Records found this ID!"));
        personRepository.delete(entity);
    }


    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Cleverson "+i);
        person.setLastName("COrdeiro "+i);
        person.setAddress("Campo Largo "+i);
        person.setGender("Male");
        return person;
    }

    public Person findById(Long id){
        logger.info("Finding id");
        return personRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
    }

}
