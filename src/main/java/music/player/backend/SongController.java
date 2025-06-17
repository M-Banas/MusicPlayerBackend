package music.player.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class SongController {

    @GetMapping("/songs")
    public List<Song> getSongs() {
        return List.of(
            new Song("song1", "Song Title", "Artist Name", "https://freepd.com/music/Study%20and%20Relax.mp3"),
            new Song("2", "Song 5", "Artist B", "https://www.bensound.com/bensound-music/bensound-summer.mp3"),
            new Song("3", "Song 3", "Artist C", "https://www.bensound.com/bensound-music/bensound-sunny.mp3"),
            new Song("1", "Song Title", "Artist Name", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
        );
    }
} 
