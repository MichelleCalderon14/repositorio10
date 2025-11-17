package com.mateo.colegio.Repositorios;

import com.mateo.colegio.Entidades.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotaRepositorio extends JpaRepository<Nota, Integer> {

    @Query("SELECT n FROM Nota n WHERE n.docente.id_docente = :idDocente")
    List<Nota> findByDocente(@Param("idDocente") Integer idDocente);

    @Query("SELECT n FROM Nota n " +
           "WHERE n.docente.id_docente = :idDocente " +
           "AND n.alumno.id_alumno = :idAlumno")
    List<Nota> findByDocenteAndAlumno(@Param("idDocente") Integer idDocente,
                                      @Param("idAlumno") Integer idAlumno);
}
