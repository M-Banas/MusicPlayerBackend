package music.player.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String username;

    public Person() {}

    public Person(String username) {
        this.username = username;
    }
}