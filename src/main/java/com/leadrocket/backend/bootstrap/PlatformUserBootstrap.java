// Creates default platform admin user on application startup
// This user is lifetime and not affected by subscription or trial

package com.leadrocket.backend.bootstrap;

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

        String adminEmail = "admin@estateflow.com";

        if (userRepository.existsByEmail(adminEmail)) {
            return; // already created
        }

        User admin = new User();
        admin.setUserType(UserType.PLATFORM);
        admin.setName("Platform Admin");
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode("ChangeThisImmediately"));
        admin.setActive(true);
        admin.setCreatedAt(Instant.now());
        admin.setUpdatedAt(Instant.now());

        userRepository.save(admin);

        System.out.println("✅ Platform admin user created: " + adminEmail);
    }
}
