package music.player.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Encja reprezentująca użytkownika aplikacji muzycznej.!
 * Użytkownik może posiadać wiele playlist.
 */
@Entity
public class Person {
    /**
     * Unikalny identyfikator użytkownika (UUID jako String).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    /**
     * Unikalny nick użytkownika.
     */
    @Column(unique = true)
    public String username;

    /**
     * Hash hasła użytkownika (BCrypt).
     */
    public String passwordHash;

    /**
     * Lista playlist należących do użytkownika.
     */
    @OneToMany(mappedBy = "owner")
    @com.fasterxml.jackson.annotation.JsonManagedReference
    public List<Playlist> playlists = new ArrayList<>();

    /**
     * Konstruktor domyślny.
     */
    public Person() {}

    /**
     * Konstruktor z parametrami.
     * @param username nick
     * @param passwordHash hash hasła
     */
    public Person(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.playlists = new ArrayList<>();
    }

    /**
     * Ustawia hash hasła na podstawie podanego hasła.
     * @param password hasło w czystym tekście
     */
    public void setPassword(String password) {
        this.passwordHash = org.springframework.security.crypto.bcrypt.BCrypt.hashpw(password, org.springframework.security.crypto.bcrypt.BCrypt.gensalt());
    }

    /**
     * Zwraca hash hasła.
     * @return hash hasła
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Zwraca id użytkownika.
     * @return id
     */
    public String getId() {
        return id;
    }
}