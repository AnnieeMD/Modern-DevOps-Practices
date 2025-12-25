package com.devops.controller;

import com.devops.model.Person;
import com.devops.service.PersonService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getPeople() {
        return personService.getPeople();
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable int id) {
        return personService.getPersonById(id);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable int id, @RequestBody Person person) {
        return personService.updatePerson(id, person);
    }

    @DeleteMapping("/{id}")
    public Person deletePerson(@PathVariable int id) {
        return personService.deletePerson(id);
    }
}

