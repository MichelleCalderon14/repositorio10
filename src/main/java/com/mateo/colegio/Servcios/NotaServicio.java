// src/main/java/com/mateo/colegio/Servicios/NotaServicio.java
package com.mateo.colegio.Servcios;

import com.mateo.colegio.Entidades.Nota;
import com.mateo.colegio.Repositorios.NotaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotaServicio {

    private final NotaRepositorio repo;

    public List<Nota> listar() {
        return repo.findAll();
    }

    public Optional<Nota> porId(Integer id) {
        return repo.findById(id);
    }

    public Nota guardar(Nota e) {
        return repo.save(e);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
