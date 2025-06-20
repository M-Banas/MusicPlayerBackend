package music.player.backend.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Encja reprezentująca playlistę w aplikacji muzycznej
 * Playlista należy do jednego użytkownika i może zawierać wiele utworów
 */
@Entity
public class Playlist {
    /**
     * Unikalny identyfikator playlisty (UUID jako String)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    /**
     * Nazwa playlisty
     */
    public String name;

    /**
     * Lista utworów w playliście
     */
    @ManyToMany
    public List<Song> songs;

    /**
     * Właściciel playlisty
     */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @com.fasterxml.jackson.annotation.JsonBackReference
    public Person owner;

    /**
     * Konstruktor domyślny
     */
    public Playlist() {}

    /**
     * Konstruktor z parametrami
     * @param name nazwa playlisty
     * @param songs lista utworów
     * @param owner właściciel playlisty
     */
    public Playlist(String name, List<Song> songs, Person owner) {
        this.name = name;
        this.songs = songs;
        this.owner = owner;
    }

    /**
     * Zwraca id playlisty
     * @return id playlisty
     */
    public String getId() {
        return id;
    }
}