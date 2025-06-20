package music.player.backend.repository;

import music.player.backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repozytorium do zarządzania encjami Person w bazie danych
 */
public interface PersonRepository extends JpaRepository<Person, String> {
    
    /**
     * Wyszukuje użytkownika po jego nicku
     * @param username nick użytkownika
     * @return znaleziony użytkownik lub null
     */
    Person findByUsername(String username);
}