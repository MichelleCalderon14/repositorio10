package com.mateo.colegio.Repositorios;

import com.mateo.colegio.Entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepositorio extends JpaRepository<Rol, Integer> {

    // 🔹 Nuevo método para buscar por nombre del rol
    Optional<Rol> findByNombre(String nombre);
}
