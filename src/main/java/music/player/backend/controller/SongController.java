package music.player.backend.controller;

import music.player.backend.model.Song;
import music.player.backend.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontroler REST do zarządzania piosenkami
 * Pozwala na dodawanie, usuwanie i pobieranie piosenek
 */
@RestController
@RequestMapping("/songs")
public class SongController {
    /** Repozytorium piosenek */
    @Autowired
    private SongRepository songRepository;

    /**
     * Pobiera wszystkie piosenki
     * @return lista wszystkich piosenek
     */
    @GetMapping
    public List<Song> getSongs() {
        return songRepository.findAll();
    }

    /**
     * Dodaje nową piosenkę
     * @param song piosenka w formacie JSON
     * @return dodana piosenka
     */
    @PostMapping("/add")
    public Song addSong(@RequestBody Song song) {
        return songRepository.save(song);
    }

    /**
     * Usuwa piosenkę po ID
     * @param id identyfikator piosenki
     */
    @DeleteMapping("/remove/{id}")
    public void removeSong(@PathVariable String id) {
        songRepository.deleteById(id);
    }
}