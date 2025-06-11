package music.player.backend.controller;

import music.player.backend.model.Song;
import music.player.backend.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongController {

    @Autowired
    private SongRepository songRepository;

    @GetMapping("/songs")
    public List<Song> getSongs() {
        return songRepository.findAll();
    }

    @PostMapping("/songs/add")
    public Song addSong(@RequestBody Song song) {
        return songRepository.save(song);
    }

    @DeleteMapping("/songs/remove/{id}")
    public void removeSong(@PathVariable String id) {
        songRepository.deleteById(id);
    }
}