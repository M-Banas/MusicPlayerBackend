package music.player.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String id;

    public String username;

    public String passwordHash;

    @OneToMany(mappedBy = "owner")
    @com.fasterxml.jackson.annotation.JsonManagedReference
    public List<Playlist> playlists = new ArrayList<>(); // <-- inicjalizacja

    public Person() {}

    public Person(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.playlists = new ArrayList<>(); // <-- inicjalizacja
    }

    public void setPassword(String password) {
        this.passwordHash = org.springframework.security.crypto.bcrypt.BCrypt.hashpw(password, org.springframework.security.crypto.bcrypt.BCrypt.gensalt());
    }
    public String getPasswordHash() {
        return passwordHash;
    }

    public String getId() {
        return id;
    }

}