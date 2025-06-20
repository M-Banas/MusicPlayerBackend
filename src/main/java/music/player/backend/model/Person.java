package music.player.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Column(unique = true)
    public String username;

    public String passwordHash;

    @OneToMany(mappedBy = "owner")//powiazanie playlisty z ownerem
    @com.fasterxml.jackson.annotation.JsonManagedReference
    public List<Playlist> playlists = new ArrayList<>(); 

    public Person() {}

    public Person(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.playlists = new ArrayList<>(); 
    }

    public void setPassword(String password) {
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public String getPasswordHash() {
        return passwordHash;
    }

    public String getId() {
        return id;
    }

}