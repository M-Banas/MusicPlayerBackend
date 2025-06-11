package music.player.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String username;

    @OneToMany(mappedBy = "owner")
    @com.fasterxml.jackson.annotation.JsonManagedReference
    public List<Playlist> playlists = new ArrayList<>(); // <-- inicjalizacja

    public Person() {}

    public Person(String username) {
        this.username = username;
        this.playlists = new ArrayList<>(); // <-- inicjalizacja
    }
}