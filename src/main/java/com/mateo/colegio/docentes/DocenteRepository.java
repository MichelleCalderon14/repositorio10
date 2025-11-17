package com.mateo.colegio.docentes;

import com.mateo.colegio.Entidades.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Integer> {
}
