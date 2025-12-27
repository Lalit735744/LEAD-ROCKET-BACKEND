package com.leadrocket.backend.bootstrap;

// What this file is for:
// Creates a lifetime platform admin user at startup (no subscription)

import com.leadrocket.backend.users.model.User;
import com.leadrocket.backend.users.model.UserType;
import com.leadrocket.backend.users.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class PlatformUserBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PlatformUserBootstrap(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        String adminEmail = "admin@gmail.com";

        if (userRepository.existsByEmail(adminEmail)) {
            return;
        }

        User admin = new User();
        admin.setEmail(adminEmail);
        admin.setName("MR.X");
        admin.setPassword(passwordEncoder.encode("Lalit@123"));
        admin.setUserType(UserType.PLATFORM);
        admin.setActive(true);
        admin.setCreatedAt(Instant.now());
        admin.setUpdatedAt(Instant.now());

        userRepository.save(admin);
    }
}
