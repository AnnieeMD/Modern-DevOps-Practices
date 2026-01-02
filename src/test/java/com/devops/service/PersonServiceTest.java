package com.devops.service;

import com.devops.model.Person;
import com.devops.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class PersonServiceTest {

    private static final int PERSON_ONE_ID = 1;
    private static final int PERSON_TWO_ID = 2;
    private static final int NEW_PERSON_ID = 3;
    private static final int NON_EXISTING_PERSON_ID = 999;
    private static final String PERSON_ONE_NAME = "Person One";
    private static final String PERSON_TWO_NAME = "Person Two";
    private static final String JOHN_DOE = "John Doe";
    private static final String UPDATED_NAME = "Updated Name";

    private PersonRepository personRepository;
    private PersonService personService;
    private Person personOne;
    private Person personTwo;

    @BeforeEach
    void setUp() {
        personRepository = mock(PersonRepository.class);
        personService = new PersonService(personRepository);
        
        personOne = new Person(PERSON_ONE_ID, PERSON_ONE_NAME);
        personTwo = new Person(PERSON_TWO_ID, PERSON_TWO_NAME);
    }

    @Test
    void givenNewService_whenConstructed_thenInitializesWithTwoPeople() {
        when(personRepository.findAll()).thenReturn(List.of(personOne, personTwo));
        
        List<Person> people = personService.getPeople();

        assertEquals(2, people.size());
        assertPersonDetails(people.get(0), PERSON_ONE_ID, PERSON_ONE_NAME);
        assertPersonDetails(people.get(1), PERSON_TWO_ID, PERSON_TWO_NAME);
    }

    @Test
    void givenPeopleExist_whenGetPeople_thenReturnAllPeople() {
        when(personRepository.findAll()).thenReturn(List.of(personOne, personTwo));
        
        List<Person> people = personService.getPeople();

        assertNotNull(people);
        assertEquals(2, people.size());
    }

    @Test
    void givenPersonExists_whenGetPersonById_thenReturnPerson() {
        when(personRepository.findById(PERSON_ONE_ID)).thenReturn(Optional.of(personOne));
        
        Person person = personService.getPersonById(PERSON_ONE_ID);

        assertNotNull(person);
        assertPersonDetails(person, PERSON_ONE_ID, PERSON_ONE_NAME);
    }

    @Test
    void givenPersonNotExists_whenGetPersonById_thenReturnNull() {
        when(personRepository.findById(NON_EXISTING_PERSON_ID)).thenReturn(Optional.empty());
        
        Person person = personService.getPersonById(NON_EXISTING_PERSON_ID);

        assertNull(person);
    }

    @Test
    void givenValidPerson_whenCreatePerson_thenReturnCreatedPerson() {
        Person newPerson = new Person(0, JOHN_DOE);
        Person savedPerson = new Person(NEW_PERSON_ID, JOHN_DOE);
        when(personRepository.save(newPerson)).thenReturn(savedPerson);

        Person createdPerson = personService.createPerson(newPerson);

        assertNotNull(createdPerson);
        assertPersonDetails(createdPerson, NEW_PERSON_ID, JOHN_DOE);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "   "})
    void givenInvalidName_whenCreatePerson_thenReturnNull(String invalidName) {
        Person newPerson = new Person(0, invalidName);

        Person createdPerson = personService.createPerson(newPerson);

        assertNull(createdPerson);
    }

    @Test
    void givenValidUpdate_whenUpdatePerson_thenReturnUpdatedPerson() {
        Person updateData = new Person(0, UPDATED_NAME);
        Person updatedPersonMock = new Person(PERSON_ONE_ID, UPDATED_NAME);
        
        when(personRepository.findById(PERSON_ONE_ID)).thenReturn(Optional.of(personOne));
        when(personRepository.save(personOne)).thenReturn(updatedPersonMock);

        Person updatedPerson = personService.updatePerson(PERSON_ONE_ID, updateData);

        assertNotNull(updatedPerson);
        assertPersonDetails(updatedPerson, PERSON_ONE_ID, UPDATED_NAME);
        verify(personRepository).save(personOne);
    }

    @Test
    void givenPersonNotExists_whenUpdatePerson_thenReturnNull() {
        Person updateData = new Person(0, UPDATED_NAME);
        when(personRepository.findById(NON_EXISTING_PERSON_ID)).thenReturn(Optional.empty());

        Person updatedPerson = personService.updatePerson(NON_EXISTING_PERSON_ID, updateData);

        assertNull(updatedPerson);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "   " })
    void givenInvalidName_whenUpdatePerson_thenReturnNull(String invalidName) {
        Person updateData = new Person(0, invalidName);
        when(personRepository.findById(PERSON_ONE_ID)).thenReturn(Optional.of(personOne));

        Person updatedPerson = personService.updatePerson(PERSON_ONE_ID, updateData);

        assertNull(updatedPerson);
    }

    @Test
    void givenPersonExists_whenDeletePerson_thenReturnDeletedPerson() {
        when(personRepository.findById(PERSON_ONE_ID)).thenReturn(Optional.of(personOne));
        
        Person deletedPerson = personService.deletePerson(PERSON_ONE_ID);

        assertNotNull(deletedPerson);
        assertPersonDetails(deletedPerson, PERSON_ONE_ID, PERSON_ONE_NAME);
        verify(personRepository).delete(personOne);
    }

    @Test
    void givenPersonNotExists_whenDeletePerson_thenReturnNull() {
        when(personRepository.findById(NON_EXISTING_PERSON_ID)).thenReturn(Optional.empty());
        
        Person deletedPerson = personService.deletePerson(NON_EXISTING_PERSON_ID);

        assertNull(deletedPerson);
    }

    private void assertPersonDetails(Person person, int expectedId, String expectedName) {
        assertEquals(expectedId, person.getId());
        assertEquals(expectedName, person.getName());
    }
}
