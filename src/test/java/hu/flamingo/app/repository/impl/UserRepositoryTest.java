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
    void testFindAllActive_withConstructor() {
        // Előkészítés – konstruktoros stílusban
        repo.save(new User("Kiss", "Béla", true));
        repo.save(new User("Nagy", "Éva", false));
        repo.save(new User("Tóth", "Péter", true));

        // Lekérdezés
        List<User> activeUsers = repo.findAllActive();

        // Ellenőrzés
        assertFalse(activeUsers.isEmpty(), "Nem talált aktív felhasználót");
        assertTrue(activeUsers.stream().allMatch(User::isActive),
                "Csak aktív userek térjenek vissza");
    }
}
