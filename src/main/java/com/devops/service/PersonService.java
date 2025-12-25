package com.devops.service;

import com.devops.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    private final List<Person> people = new ArrayList<>();
    private int idCounter = 1;

    public PersonService() {
        people.add(new Person(idCounter++, "Person One"));
        people.add(new Person(idCounter++, "Person Two"));
    }

    public List<Person> getPeople() {
        return people;
    }

    public Person getPersonById(int id) {
        return people.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Person createPerson(Person person) {
        if (person.getName() == null || person.getName().isBlank()) {
            return null;
        }
        Person newPerson = new Person(idCounter++, person.getName());
        people.add(newPerson);
        return newPerson;
    }

    public Person updatePerson(int id, Person newPerson) {
        for (Person person : people) {
            if (person.getId() == id && newPerson.getName() != null && !newPerson.getName().isBlank()) {
                person.setName(newPerson.getName());
                return person;
            }
        }
        return null;
    }

    public Person deletePerson(int id) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getId() == id) {
                return people.remove(i);
            }
        }
        return null;
    }
}
