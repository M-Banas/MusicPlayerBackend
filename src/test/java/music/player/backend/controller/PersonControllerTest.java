package music.player.backend.controller;

import music.player.backend.model.Person;
import music.player.backend.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PersonControllerTest {

    @Mock // Tworzy mock repozytorium PersonRepository
    private PersonRepository personRepository;

    @InjectMocks // Wstrzykuje mockowane repozytorium do testowanego kontrolera
    private PersonController personController;

    @BeforeEach
    void setUp() {
        // Inicjalizuje mocki przed każdym testem
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addPerson_shouldReturnSavedPerson() {
        // tworzenie Person z danymi testowymi
        Person person = new Person();
        person.username = "testuser";
        person.setPassword("testpass");
        when(personRepository.save(any(Person.class))).thenReturn(person);// Gdy wywołamy save na repozytorium, zwróć przygotowaną osobę

        Person result = personController.addPerson("testuser", "testpass");//wywołanie
        assertEquals("testuser", result.username);//czy username to testuser
        assertTrue(BCrypt.checkpw("testpass", result.getPasswordHash()));//czy hasło poprawnie zahasowanane
    }

    @Test
    void deletePerson_shouldCallRepository() {
        // Przygotowanie: nie rób nic przy usuwaniu po id
        doNothing().when(personRepository).deleteById("1");
        // Wywołanie metody kontrolera
        personController.deletePerson("1");
    }

    @Test
    void login_shouldReturnIdOnSuccess() {
        // Tworzenie testowego uzytkownika z haslem
        Person person = new Person();
        person.id = "abc123";
        person.username = "user";
        person.setPassword("pass");
        when(personRepository.findByUsername("user")).thenReturn(person);//czy repozytorium zwroci szukajac po nazwie


        String result = personController.login("user", "pass");//logowanie
        assertEquals("abc123", result);//czy zwrocone id jest poprawne
    }

    @Test
    void login_shouldReturnNullOnFailure() {
        when(personRepository.findByUsername("user")).thenReturn(null);//nie ma uzytkownika w repozytoriun
        String result = personController.login("user", "wrongpass");//logowanie ze zlym haslem
        assertNull(result);//czy zwrocono null (nieudane logowanie)
    }
}
