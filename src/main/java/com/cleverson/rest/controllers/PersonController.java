package com.cleverson.rest.controllers;

import com.cleverson.rest.exceptions.UnsupportMathOperationException;
import com.cleverson.rest.model.Person;
import com.cleverson.rest.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService service;


    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person findById(@PathVariable(value = "id")  Long id) throws Exception {
        System.out.println("Person ID="+id);
       return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person create(@RequestBody Person  person) throws Exception {
       return service.create(person);
    }

    @RequestMapping(method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Person> findAll() throws Exception {
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.PUT,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person update(@RequestBody Person person){
        return service.update(person);
    }

    @RequestMapping(value = "/{id}", 
                    method = RequestMethod.DELETE
    )
    public void delete(@PathVariable(value = "id") Long id){
        service.delete(id);
    }



}
