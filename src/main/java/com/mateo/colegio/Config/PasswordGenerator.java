package com.mateo.colegio.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordGenerator {

    @Bean
    public CommandLineRunner generatePasswordHash(PasswordEncoder encoder) {
        return args -> {
            String raw = "mariacabrera";  // <- contraseña que quieres para pepecabrera
            String hash = encoder.encode(raw);
            System.out.println("===== HASH PARA pepecabrera =====");
            System.out.println(hash);
            System.out.println("===== FIN HASH =====");
        };
    }
}
