package com.example.hit_networking_base.config;

import com.example.hit_networking_base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class AdminInitializer {
    private final UserService userService;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.username}")
    private String adminName;

    @Bean
    public CommandLineRunner addDefaultAdmin() {
        return args -> {
            if (userService.addAdmin(adminName, adminPassword)) {
                System.out.println("Create admin success.");
            } else {
                System.out.println("Admin already exists.");
            }
        };
    }
}
