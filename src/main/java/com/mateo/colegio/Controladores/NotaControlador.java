package com.mateo.colegio.Controladores;

import com.mateo.colegio.Entidades.Alumno;
import com.mateo.colegio.Entidades.Nota;
import com.mateo.colegio.Repositorios.AlumnoRepositorio;
import com.mateo.colegio.Repositorios.NotaRepositorio;
import com.mateo.colegio.Entidades.Docente;
import com.mateo.colegio.docentes.DocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotaControlador {

    private final NotaRepositorio notaRepo;
    private final AlumnoRepositorio alumnoRepo;
    private final DocenteRepository docenteRepo;

    // === Registrar nota de un alumno con un docente ===
    @PostMapping("/docentes/{idDocente}/alumnos/{idAlumno}/notas")
    public ResponseEntity<Nota> crearNota(
            @PathVariable("idDocente") Integer idDocente,
            @PathVariable("idAlumno") Integer idAlumno,
            @RequestBody Nota body
    ) {

        Alumno alumno = alumnoRepo.findById(idAlumno)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        Docente docente = docenteRepo.findById(idDocente)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        body.setAlumno(alumno);
        body.setDocente(docente);

        Nota guardada = notaRepo.save(body);
        return ResponseEntity.ok(guardada);
    }

    // === Obtener notas de un alumno con un docente ===
    @GetMapping("/docentes/{idDocente}/alumnos/{idAlumno}/notas")
    public ResponseEntity<List<Nota>> listarNotasAlumnoDocente(
            @PathVariable("idDocente") Integer idDocente,
            @PathVariable("idAlumno") Integer idAlumno
    ) {
        List<Nota> notas = notaRepo.findByDocenteAndAlumno(idDocente, idAlumno);
        return ResponseEntity.ok(notas);
    }
}
