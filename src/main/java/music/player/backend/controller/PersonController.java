package music.player.backend.controller;

import music.player.backend.model.Person;
import music.player.backend.model.Playlist;
import music.player.backend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontroler REST do zarządzania użytkownikami (Person).
 * Pozwala na rejestrację, usuwanie, pobieranie playlist oraz logowanie użytkowników.
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    /** Repozytorium użytkowników */
    @Autowired
    private PersonRepository personRepository;

    /**
     * Dodaje nowego użytkownika.
     * @param username unikalny nick użytkownika
     * @param password hasło użytkownika
     * @return utworzony użytkownik
     * @throws RuntimeException jeśli username już istnieje
     */
    @PostMapping("/add")
    public Person addPerson(@RequestParam String username, @RequestParam String password) {
        if (personRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }
        Person person = new Person();
        person.username = username;
        person.setPassword(password);
        return personRepository.save(person);
    }

    /**
     * Usuwa użytkownika po ID.
     * @param id identyfikator użytkownika
     */
    @DeleteMapping("/delete/{id}")
    public void deletePerson(@PathVariable String id) {
        personRepository.deleteById(id);
    }

    /**
     * Zwraca playlisty danego użytkownika.
     * @param id identyfikator użytkownika
     * @return lista playlist użytkownika
     */
    @GetMapping("/{id}/playlists")
    public List<Playlist> getPersonPlaylists(@PathVariable String id) {
        Person person = personRepository.findById(id).orElseThrow();
        return person.playlists;
    }

    /**
     * Loguje użytkownika na podstawie loginu i hasła.
     * @param username nick użytkownika
     * @param password hasło użytkownika
     * @return id użytkownika jeśli logowanie się powiodło, null w przeciwnym razie
     */
    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Person person = personRepository.findByUsername(username);
        if (person != null && person.getPasswordHash() != null && BCrypt.checkpw(password, person.getPasswordHash())) {
            return person.getId(); 
        }
        return null; 
    }
}