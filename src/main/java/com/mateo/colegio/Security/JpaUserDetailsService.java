package com.mateo.colegio.Security;

import com.mateo.colegio.Entidades.Usuario;
import com.mateo.colegio.Repositorios.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaUserDetailsService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario u = usuarioRepositorio.findByUsernameAndActivoTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Cargar rol (ya no causa LazyInitializationException gracias al fetch = EAGER)
        String rol = u.getRol().getNombre();

        return User.withUsername(u.getUsername())
                .password(u.getPasswordHash())   // Contraseña encriptada (BCrypt)
                .roles(rol)                      // Spring añadirá automáticamente ROLE_
                .build();
    }
}
