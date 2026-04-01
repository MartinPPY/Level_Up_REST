package com.levelup.app.repositories;

import com.levelup.app.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Should find user by email")
    void shouldFindUserByEmail() {
        // Given
        User user = new User();
        user.setRun("123456789"); // 9 caracteres
        user.setName("Test");
        user.setLastname("User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setAddres("Test Address");
        
        entityManager.persist(user);
        entityManager.flush();

        // When
        Optional<User> found = userRepository.findUserByEmail("test@example.com");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("test@example.com");
        assertThat(found.get().getRun()).isEqualTo("123456789");
    }

    @Test
    @DisplayName("Should find user by run")
    void shouldFindUserByRun() {
        // Given
        User user = new User();
        user.setRun("987654321"); // 9 caracteres
        user.setName("John");
        user.setLastname("Doe");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setAddres("Other Address");
        
        entityManager.persist(user);
        entityManager.flush();

        // When
        Optional<User> found = userRepository.findByRun("987654321");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getRun()).isEqualTo("987654321");
        assertThat(found.get().getEmail()).isEqualTo("john@example.com");
    }

    @Test
    @DisplayName("Should return empty when email does not exist")
    void shouldReturnEmptyWhenEmailDoesNotExist() {
        // When
        Optional<User> found = userRepository.findUserByEmail("nonexistent@example.com");

        // Then
        assertThat(found).isEmpty();
    }
}
