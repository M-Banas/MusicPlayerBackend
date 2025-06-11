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

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/create")
    public Playlist createPlaylist(@RequestParam String name, @RequestParam Long ownerId) {
        Person owner = personRepository.findById(ownerId).orElseThrow();
        Playlist playlist = new Playlist(name, new ArrayList<>(), owner);
        return playlistRepository.save(playlist);
    }

    @DeleteMapping("/remove/{id}")
    public void removePlaylist(@PathVariable Long id) {
        playlistRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public Playlist getPlaylist(@PathVariable Long id) {
        return playlistRepository.findById(id).orElse(null);
    }

    @PostMapping("/{playlistId}/addSong")
    public Playlist addSongToPlaylist(@PathVariable Long playlistId, @RequestParam String songId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow();
        Song song = songRepository.findById(songId).orElseThrow();
        playlist.songs.add(song);
        return playlistRepository.save(playlist);
    }

    @PostMapping("/{playlistId}/removeSong")
    public Playlist removeSongFromPlaylist(@PathVariable Long playlistId, @RequestParam String songId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow();
        playlist.songs.removeIf(song -> song.id.equals(songId));
        return playlistRepository.save(playlist);
    }
}