package music.player.backend.controller;

import music.player.backend.model.Person;
import music.player.backend.model.Playlist;
import music.player.backend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/add")
    public Person addPerson(@RequestParam String username, @RequestParam String password) {
        Person person = new Person();
        person.username = username;
        person.setPassword(password);
        return personRepository.save(person);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePerson(@PathVariable String id) {
        personRepository.deleteById(id);
    }

    @GetMapping("/{id}/playlists")
    public List<Playlist> getPersonPlaylists(@PathVariable String id) {
        Person person = personRepository.findById(id).orElseThrow();
        return person.playlists;
    }

    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Person person = personRepository.findByUsername(username);
        if (person != null && person.getPasswordHash() != null && 
            org.springframework.security.crypto.bcrypt.BCrypt.checkpw(password, person.getPasswordHash())) {
            return person.getId(); // Login successful
        }
        return null; // Login failed
    }
}