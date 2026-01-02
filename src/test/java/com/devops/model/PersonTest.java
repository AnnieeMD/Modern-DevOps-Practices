package com.devops.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PersonTest {

    private static final int DEFAULT_ID = 0;
    private static final int TEST_ID_1 = 1;
    private static final int TEST_ID_42 = 42;
    private static final String JOHN_DOE = "John Doe";
    private static final String JANE_SMITH = "Jane Smith";
    private static final String TEST_PERSON = "Test Person";
    private static final String ORIGINAL_NAME = "Original Name";
    private static final String UPDATED_NAME = "Updated Name";

    @Test
    void givenDefaultConstructor_whenCreated_thenHasDefaultValues() {
        Person person = new Person();

        assertNotNull(person);
        assertEquals(DEFAULT_ID, person.getId());
        assertNull(person.getName());
    }

    @Test
    void givenParameterizedConstructor_whenCreated_thenHasProvidedValues() {
        Person person = new Person(TEST_ID_1, JOHN_DOE);
        assertEquals(TEST_ID_1, person.getId());
        assertEquals(JOHN_DOE, person.getName());
    }

    @Test
    void givenNameOnlyConstructor_whenCreated_thenHasDefaultIdAndProvidedName() {
        Person person = new Person(JOHN_DOE);
        assertEquals(DEFAULT_ID, person.getId());
        assertEquals(JOHN_DOE, person.getName());
    }

    @Test
    void givenPerson_whenSetId_thenIdIsUpdated() {
        Person person = new Person();
        
        person.setId(TEST_ID_42);
        
        assertEquals(TEST_ID_42, person.getId());
    }

    @Test
    void givenPerson_whenGetId_thenReturnCorrectId() {
        Person person = createPerson(TEST_ID_42, TEST_PERSON);

        assertEquals(TEST_ID_42, person.getId());
    }

    @Test
    void givenPerson_whenGetName_thenReturnCorrectName() {
        Person person = createPerson(TEST_ID_1, JANE_SMITH);

        assertEquals(JANE_SMITH, person.getName());
    }

    @Test
    void givenPerson_whenSetName_thenNameIsUpdated() {
        Person person = createPerson(TEST_ID_1, ORIGINAL_NAME);

        person.setName(UPDATED_NAME);

        assertEquals(UPDATED_NAME, person.getName());
    }

    @Test
    void givenPerson_whenSetNameWithNull_thenNameIsNull() {
        Person person = createPerson(TEST_ID_1, ORIGINAL_NAME);

        person.setName(null);

        assertNull(person.getName());
    }

    @Test
    void givenPerson_whenSetNameWithEmptyString_thenNameIsEmpty() {
        Person person = createPerson(TEST_ID_1, ORIGINAL_NAME);

        person.setName("");

        assertEquals("", person.getName());
    }

    private Person createPerson(int id, String name) {
        return new Person(id, name);
    }
}
