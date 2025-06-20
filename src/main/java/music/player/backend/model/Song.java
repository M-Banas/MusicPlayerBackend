package music.player.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Encja reprezentująca utwór muzyczny w aplikacji
 * Zawiera podstawowe informacje o utworze, takie jak tytuł, wykonawca i URL
 */
@Entity
public class Song {
    /**
     * Unikalny identyfikator utworu
     */
    @Id
    public String id;

    /**
     * Tytuł utworu
     */
    public String title;

    /**
     * Wykonawca utworu
     */
    public String artist;

    /**
     * URL do pliku z utworem
     */
    public String url;

    /**
     * Konstruktor domyślny
     */
    public Song() {}

    /**
     * Konstruktor z parametrami
     * @param id identyfikator utworu
     * @param title tytuł utworu
     * @param artist wykonawca utworu
     * @param url URL do pliku z utworem
     */
    public Song(String id, String title, String artist, String url) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.url = url;
    }
}