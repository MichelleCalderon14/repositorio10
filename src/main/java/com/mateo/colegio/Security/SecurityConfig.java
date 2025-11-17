package com.mateo.colegio.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Sin sesiones (JWT stateless)
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Login público
                .requestMatchers("/api/auth/login").permitAll()
                // Permitir preflight de CORS (si lo necesitas)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Solo ADMIN puede manejar usuarios
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                // ADMIN o DOCENTE pueden manejar docentes y notas
                .requestMatchers("/api/docentes/**", "/api/notas/**")
                    .hasAnyRole("ADMIN", "DOCENTE")

                // ===== REGLAS PARA ALUMNOS =====
                // Ver alumnos: ADMIN, DOCENTE, ALUMNO
                .requestMatchers(HttpMethod.GET, "/api/alumnos/**")
                    .hasAnyRole("ADMIN", "DOCENTE", "ALUMNO")

                // Crear o actualizar alumnos: ADMIN o DOCENTE
                .requestMatchers(HttpMethod.POST, "/api/alumnos/**")
                    .hasAnyRole("ADMIN", "DOCENTE")
                .requestMatchers(HttpMethod.PUT, "/api/alumnos/**")
                    .hasAnyRole("ADMIN", "DOCENTE")

                // Eliminar alumnos: solo ADMIN
                .requestMatchers(HttpMethod.DELETE, "/api/alumnos/**")
                    .hasRole("ADMIN")
                // ===== FIN REGLAS PARA ALUMNOS =====

                // Todo lo demás requiere estar autenticado
                .anyRequest().authenticated()
            )
            // Filtro JWT antes del de usuario/contraseña
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Necesario para que AuthController pueda usar AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
