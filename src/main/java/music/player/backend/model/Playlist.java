package music.player.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @ManyToMany
    public List<Song> songs;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @com.fasterxml.jackson.annotation.JsonBackReference
    public Person owner;

    public Playlist() {}

    public Playlist(String name, List<Song> songs, Person owner) {
        this.name = name;
        this.songs = songs;
        this.owner = owner;
    }
}