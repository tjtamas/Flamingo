package hu.flamingo.app.repository.impl;

import hu.flamingo.app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository repo;

    @BeforeEach
    void setup() {
        repo = new UserRepository();
    }

    @Test
    void testFindAllActive_withBuilder() {
        // Előkészítés – builder stílusban
        repo.save(User.builder()
                .lastName("Kiss")
                .firstName("Béla")
                .isActive(true)
                .build());

        repo.save(User.builder()
                .lastName("Nagy")
                .firstName("Éva")
                .isActive(false)
                .build());

        repo.save(User.builder()
                .lastName("Tóth")
                .firstName("Péter")
                .isActive(true)
                .build());

        // Lekérdezés
        List<User> activeUsers = repo.findAllActive();

        // Ellenőrzés
        assertFalse(activeUsers.isEmpty(), "Nem talált aktív felhasználót");
        assertTrue(activeUsers.stream().allMatch(User::isActive), "Csak aktív userek térjenek vissza");
    }
}
