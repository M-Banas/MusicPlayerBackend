package music.player.backend.repository;

import music.player.backend.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repozytorium do zarządzania encjami Playlist w bazie danych
 */
public interface PlaylistRepository extends JpaRepository<Playlist, String> {
}