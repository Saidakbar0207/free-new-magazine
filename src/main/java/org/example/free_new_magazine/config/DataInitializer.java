package org.example.free_new_magazine.config;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.Role;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.repository.UserRepository;
import org.example.free_new_magazine.service.AuditLogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.password}")
    private String adminPassword;


    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "saidakbar@gmail.com";

        if(userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User();
            admin.setFirstName("Saidakbar");
            admin.setLastName("Nematullayev");
            admin.setUsername("Saidakbar");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);
            System.out.println(" Admin user yaratildi: " + adminEmail);
        } else {
            System.out.println("Admin user allaqachon mavjud " + adminEmail);
        }
    }
}
