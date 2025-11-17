package com.mateo.colegio.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Sin sesión y sin CSRF (API REST con JWT)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // 🔓 Endpoints públicos
                .requestMatchers("/error").permitAll()
                .requestMatchers(HttpMethod.POST,
                        "/api/auth/login",   // login nuevo
                        "/api/login"         // (opcional) login antiguo, por si acaso
                ).permitAll()

                // 🔐 Solo ADMIN puede gestionar roles y usuarios
                .requestMatchers("/api/roles/**", "/api/usuarios/**")
                    .hasRole("ADMIN")

                // 🔐 ADMIN y DOCENTE pueden usar /api/docentes y /api/notas
                .requestMatchers("/api/docentes/**", "/api/notas/**")
                    .hasAnyRole("ADMIN", "DOCENTE")

                // 🔐 Cualquier otra cosa requiere estar autenticado
                .anyRequest().authenticated()
            );

        // Filtro JWT antes del UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
