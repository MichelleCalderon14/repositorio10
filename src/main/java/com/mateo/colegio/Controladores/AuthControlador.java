package com.mateo.colegio.Controladores;

import com.mateo.colegio.Dto.AuthRolDto;
import com.mateo.colegio.Dto.AuthUserDto;
import com.mateo.colegio.Dto.LoginRequest;
import com.mateo.colegio.Dto.LoginResponse;
import com.mateo.colegio.Entidades.Usuario;
import com.mateo.colegio.Repositorios.UsuarioRepositorio;
import com.mateo.colegio.Servcios.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthControlador {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    @Operation(summary = "Autentica y devuelve JWT + datos de usuario/rol")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        // 1) Buscar usuario activo por username
        Optional<Usuario> opt = usuarioRepositorio.findByUsernameAndActivoTrue(req.getUsername());
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
        }

        Usuario u = opt.get();

        // 2) Verificar contraseña con BCrypt  👈 AQUÍ ESTABA EL ERROR
        if (!passwordEncoder.matches(req.getPassword(), u.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
        }

        // 3) Construir principal con su rol
        String rol = u.getRol().getNombre();  // ADMIN, DOCENTE, ALUMNO, etc.
        User principal = (User) User
                .withUsername(u.getUsername())
                .password(u.getPasswordHash())
                .roles(rol)
                .build();

        // 4) Generar token JWT
        String token = jwtService.generateToken(principal);

        // 5) Armar DTOs para enviar a Angular
        AuthRolDto rolDto = new AuthRolDto(
                u.getRol().getIdRol(),
                u.getRol().getNombre()
        );

        AuthUserDto userDto = new AuthUserDto(
                u.getIdUsuario(),
                u.getUsername(),
                u.getEmail(),
                u.getActivo(),   // 👈 usa getActivo(), no isActivo()
                rolDto
        );

        LoginResponse response = new LoginResponse(
                token,
                "Bearer",
                u.getUsername(),
                userDto
        );

        return ResponseEntity.ok(response);
    }
}
