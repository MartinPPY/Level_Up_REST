package com.levelup.app.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.levelup.app.models.User;
import com.levelup.app.repositories.ComunaRepository;
import com.levelup.app.repositories.RoleRepository;
import com.levelup.app.repositories.UserRepository;

@Configuration
@Profile("!test")
public class DataInitializer {

    @Bean
    CommandLineRunner init(
        UserRepository userRepository,
        PasswordEncoder encoder,
        ComunaRepository comunaRepository,
        RoleRepository roleRepository
    ) {
        return args -> {
            // Solo inicializar si no hay datos o según lógica de negocio
            if (userRepository.count() == 0) {
                comunaRepository.findById(1L).ifPresent(comuna -> {
                    roleRepository.findById(3L).ifPresent(role -> {
                        User user = new User("21340282K", "admin", "admin", "admin@gmail.com",
                                LocalDate.of(1990, 1, 1), encoder.encode("admin"), "Cerrillos #213", comuna, role);
                        userRepository.save(user);
                    });

                    roleRepository.findById(2L).ifPresent(role2 -> {
                        User user2 = new User("111111111", "vendedor", "vendedor", "vendedor@gmail.com",
                                LocalDate.of(1990, 1, 1), encoder.encode("vendedor"), "Cerrillos #213", comuna, role2);
                        userRepository.save(user2);
                    });
                });
            }
        };
    }
}
