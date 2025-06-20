package music.player.backend.repository;

import music.player.backend.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repozytorium do zarządzania encjami Song w bazie danych
 */
public interface SongRepository extends JpaRepository<Song, String> {
}