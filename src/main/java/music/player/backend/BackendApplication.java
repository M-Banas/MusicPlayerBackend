package music.player.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Główna klasa backendu.
 */
@SpringBootApplication
public class BackendApplication {

    /**
     * Główna metoda uruchamiająca aplikację.
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
