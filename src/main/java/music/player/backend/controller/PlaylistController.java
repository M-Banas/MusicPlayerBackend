package music.player.backend.controller;

import music.player.backend.model.Person;
import music.player.backend.model.Playlist;
import music.player.backend.model.Song;

import music.player.backend.repository.PersonRepository;
import music.player.backend.repository.PlaylistRepository;
import music.player.backend.repository.SongRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Kontroler REST do zarządzania playlistami
 * Pozwala na tworzenie, usuwanie, pobieranie i edycję playlist oraz zarządzanie piosenkami w playlistach
 */
@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    /** Repozytorium playlist */
    @Autowired
    private PlaylistRepository playlistRepository;
    /** Repozytorium piosenek */
    @Autowired
    private SongRepository songRepository;
    /** Repozytorium użytkowników */
    @Autowired
    private PersonRepository personRepository;

    /**
     * Tworzy nową playlistę dla użytkownika
     * @param name nazwa playlisty
     * @param ownerId id właściciela
     * @return utworzona playlista
     */
    @PostMapping("/create")
    public Playlist createPlaylist(@RequestParam String name, @RequestParam String ownerId) {
        Person owner = personRepository.findById(ownerId).orElseThrow();
        Playlist playlist = new Playlist(name, new ArrayList<>(), owner);
        return playlistRepository.save(playlist);
    }

    /**
     * Usuwa playlistę po ID
     * @param id identyfikator playlisty
     */
    @DeleteMapping("/remove/{id}")
    public void removePlaylist(@PathVariable String id) {
        playlistRepository.deleteById(id);
    }

    /**
     * Pobiera playlistę po ID
     * @param id identyfikator playlisty
     * @return playlista lub null jeśli nie istnieje
     */
    @GetMapping("/{id}")
    public Playlist getPlaylist(@PathVariable String id) {
        return playlistRepository.findById(id).orElse(null);
    }

    /**
     * Dodaje piosenkę do playlisty
     * @param playlistId id playlisty
     * @param songId id piosenki
     * @return zaktualizowana playlista
     */
    @PostMapping("/{playlistId}/addSong")
    public Playlist addSongToPlaylist(@PathVariable String playlistId, @RequestParam String songId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow();
        Song song = songRepository.findById(songId).orElseThrow();
        playlist.songs.add(song);
        return playlistRepository.save(playlist);
    }

    /**
     * Usuwa piosenkę z playlisty
     * @param playlistId id playlisty
     * @param songId id piosenki
     * @return zaktualizowana playlista
     */
    @PostMapping("/{playlistId}/removeSong")
    public Playlist removeSongFromPlaylist(@PathVariable String playlistId, @RequestParam String songId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow();
        playlist.songs.removeIf(song -> song.id.equals(songId));
        return playlistRepository.save(playlist);
    }
}