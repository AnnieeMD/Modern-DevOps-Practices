package com.devops.service;

import com.devops.model.Person;
import com.devops.repository.PersonRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostConstruct
    public void initData() {
        if (personRepository.count() == 0) {
            personRepository.save(new Person("Person One"));
            personRepository.save(new Person("Person Two"));
        }
    }

    public List<Person> getPeople() {
        return personRepository.findAll();
    }

    public Person getPersonById(int id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person createPerson(Person person) {
        if (person.getName() == null || person.getName().isBlank()) {
            return null;
        }
        return personRepository.save(person);
    }

    public Person updatePerson(int id, Person newPerson) {
        return personRepository.findById(id)
            .map(person -> {
                if (newPerson.getName() != null && !newPerson.getName().isBlank()) {
                    person.setName(newPerson.getName());
                    return personRepository.save(person);
                }
                return null;
            })
            .orElse(null);
    }

    public Person deletePerson(int id) {
        return personRepository.findById(id)
            .map(person -> {
                personRepository.delete(person);
                return person;
            })
            .orElse(null);
    }
}
