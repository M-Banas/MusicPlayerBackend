package music.player.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Song {
    @Id
    public String id;
    public String title;
    public String artist;
    public String url;

    public Song() {}

    public Song(String id, String title, String artist, String url) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.url = url;
    }
}