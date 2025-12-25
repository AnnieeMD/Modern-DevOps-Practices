package com.devops.controller;

import com.devops.model.Person;
import com.devops.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    private static final int EXISTING_PERSON_ID = 1;
    private static final int NON_EXISTING_PERSON_ID = 999;
    private static final int NEW_PERSON_ID = 3;
    private static final String JOHN_DOE = "John Doe";
    private static final String JANE_SMITH = "Jane Smith";
    private static final String NEW_PERSON = "New Person";
    private static final String UPDATED_NAME = "Updated Name";
    private static final String DELETED_PERSON = "Deleted Person";

    @Mock
    private PersonService personService;

    private PersonController personController;

    @BeforeEach
    void setUp() {
        personController = new PersonController(personService);
    }

    @Test
    void givenPeopleExist_whenGetPeople_thenReturnAllPeople() {
        List<Person> expectedPeople = Arrays.asList(
                new Person(EXISTING_PERSON_ID, JOHN_DOE),
                new Person(2, JANE_SMITH)
        );
        when(personService.getPeople()).thenReturn(expectedPeople);

        List<Person> actualPeople = personController.getPeople();

        assertEquals(expectedPeople, actualPeople);
        verify(personService).getPeople();
    }

    @Test
    void givenPersonExists_whenGetPersonById_thenReturnPerson() {
        Person expectedPerson = new Person(EXISTING_PERSON_ID, JOHN_DOE);
        when(personService.getPersonById(EXISTING_PERSON_ID)).thenReturn(expectedPerson);

        Person actualPerson = personController.getPersonById(EXISTING_PERSON_ID);

        assertEquals(expectedPerson, actualPerson);
        verify(personService).getPersonById(EXISTING_PERSON_ID);
    }

    @Test
    void givenPersonNotExists_whenGetPersonById_thenReturnNull() {
        when(personService.getPersonById(NON_EXISTING_PERSON_ID)).thenReturn(null);

        Person actualPerson = personController.getPersonById(NON_EXISTING_PERSON_ID);

        assertNull(actualPerson);
        verify(personService).getPersonById(NON_EXISTING_PERSON_ID);
    }

    @Test
    void givenValidPerson_whenCreatePerson_thenReturnCreatedPerson() {
        Person inputPerson = new Person(0, NEW_PERSON);
        Person createdPerson = new Person(NEW_PERSON_ID, NEW_PERSON);
        when(personService.createPerson(inputPerson)).thenReturn(createdPerson);

        Person actualPerson = personController.createPerson(inputPerson);

        assertEquals(createdPerson, actualPerson);
        verify(personService).createPerson(inputPerson);
    }

    @Test
    void givenInvalidPerson_whenCreatePerson_thenReturnNull() {
        Person inputPerson = new Person(0, null);
        when(personService.createPerson(inputPerson)).thenReturn(null);

        Person actualPerson = personController.createPerson(inputPerson);

        assertNull(actualPerson);
        verify(personService).createPerson(inputPerson);
    }

    @Test
    void givenValidUpdate_whenUpdatePerson_thenReturnUpdatedPerson() {
        Person inputPerson = new Person(0, UPDATED_NAME);
        Person updatedPerson = new Person(EXISTING_PERSON_ID, UPDATED_NAME);
        when(personService.updatePerson(EXISTING_PERSON_ID, inputPerson)).thenReturn(updatedPerson);

        Person actualPerson = personController.updatePerson(EXISTING_PERSON_ID, inputPerson);

        assertEquals(updatedPerson, actualPerson);
        verify(personService).updatePerson(EXISTING_PERSON_ID, inputPerson);
    }

    @Test
    void givenPersonNotExists_whenUpdatePerson_thenReturnNull() {
        Person inputPerson = new Person(0, UPDATED_NAME);
        when(personService.updatePerson(NON_EXISTING_PERSON_ID, inputPerson)).thenReturn(null);

        Person actualPerson = personController.updatePerson(NON_EXISTING_PERSON_ID, inputPerson);

        assertNull(actualPerson);
        verify(personService).updatePerson(NON_EXISTING_PERSON_ID, inputPerson);
    }

    @Test
    void givenInvalidData_whenUpdatePerson_thenReturnNull() {
        Person inputPerson = new Person(0, null);
        when(personService.updatePerson(EXISTING_PERSON_ID, inputPerson)).thenReturn(null);

        Person actualPerson = personController.updatePerson(EXISTING_PERSON_ID, inputPerson);

        assertNull(actualPerson);
        verify(personService).updatePerson(EXISTING_PERSON_ID, inputPerson);
    }

    @Test
    void givenPersonExists_whenDeletePerson_thenReturnDeletedPerson() {
        Person deletedPerson = new Person(EXISTING_PERSON_ID, DELETED_PERSON);
        when(personService.deletePerson(EXISTING_PERSON_ID)).thenReturn(deletedPerson);

        Person actualPerson = personController.deletePerson(EXISTING_PERSON_ID);

        assertEquals(deletedPerson, actualPerson);
        verify(personService).deletePerson(EXISTING_PERSON_ID);
    }

    @Test
    void givenPersonNotExists_whenDeletePerson_thenReturnNull() {
        when(personService.deletePerson(NON_EXISTING_PERSON_ID)).thenReturn(null);

        Person actualPerson = personController.deletePerson(NON_EXISTING_PERSON_ID);

        assertNull(actualPerson);
        verify(personService).deletePerson(NON_EXISTING_PERSON_ID);
    }
}
