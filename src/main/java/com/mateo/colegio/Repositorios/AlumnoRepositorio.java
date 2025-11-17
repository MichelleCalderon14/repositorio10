package com.mateo.colegio.Repositorios;

import com.mateo.colegio.Entidades.Alumno;
import com.mateo.colegio.Entidades.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlumnoRepositorio extends JpaRepository<Alumno, Integer> {

    // Buscar todos los alumnos de un docente
    List<Alumno> findByDocente(Docente docente);
}
