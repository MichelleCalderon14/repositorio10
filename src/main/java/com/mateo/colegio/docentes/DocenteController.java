package com.mateo.colegio.docentes;

import com.mateo.colegio.Dto.DocenteRegistroDto;
import com.mateo.colegio.Entidades.Alumno;
import com.mateo.colegio.Entidades.Docente;
import com.mateo.colegio.Entidades.Usuario;
import com.mateo.colegio.Entidades.Rol;
import com.mateo.colegio.Repositorios.AlumnoRepositorio;
import com.mateo.colegio.Repositorios.UsuarioRepositorio;
import com.mateo.colegio.Repositorios.RolRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
@RequiredArgsConstructor
public class DocenteController {

    private final DocenteRepository docenteRepository;
    private final DocentePerfilRepository docentePerfilRepository;
    private final AlumnoRepositorio alumnoRepositorio;

    private final UsuarioRepositorio usuarioRepositorio;
    private final RolRepositorio rolRepositorio;
    private final PasswordEncoder passwordEncoder;

    // ================= DOCENTES =================

    @GetMapping
    public List<Docente> listar() {
        return docenteRepository.findAll();
    }

    // Crear Docente + Perfil + Usuario (para login)
    @PostMapping
    public ResponseEntity<Docente> crear(@RequestBody DocenteRegistroDto dto) {

        // 1) Guardar el DOCENTE
        Docente docente = Docente.builder()
                .cedula(dto.getCedula())
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .email(dto.getEmail())
                .build();

        Docente guardado = docenteRepository.save(docente);

        // 2) Crear perfil automático
        DocentePerfil perfil = new DocentePerfil();
        perfil.setNombreCompleto(guardado.getNombres() + " " + guardado.getApellidos());
        perfil.setCargo("DOCENTE");
        perfil.setArea(null);
        perfil.setAniosExperiencia(null);
        perfil.setFotografiaUrl(null);

        docentePerfilRepository.save(perfil);

        // 3) Crear usuario para login con rol DOCENTE
        Rol rolDocente = rolRepositorio.findByNombre("DOCENTE")
                .orElseThrow(() -> new RuntimeException("Rol DOCENTE no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        usuario.setEmail(dto.getEmail());
        usuario.setActivo(true);
        usuario.setRol(rolDocente);

        usuarioRepositorio.save(usuario);

        return ResponseEntity.ok(guardado);
    }

    // ================= ALUMNOS POR DOCENTE =================

    // 1) Crear alumno para un docente específico
    @PostMapping("/{idDocente}/alumnos")
    public ResponseEntity<Alumno> crearAlumnoParaDocente(
            @PathVariable("idDocente") Integer idDocente,
            @RequestBody Alumno alumno) {

        Docente docente = docenteRepository.findById(idDocente)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        alumno.setDocente(docente);
        Alumno guardado = alumnoRepositorio.save(alumno);
        return ResponseEntity.ok(guardado);
    }

    // 2) Listar alumnos de un docente específico
    @GetMapping("/{idDocente}/alumnos")
    public ResponseEntity<List<Alumno>> listarAlumnosDeDocente(
            @PathVariable("idDocente") Integer idDocente) {

        Docente docente = docenteRepository.findById(idDocente)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        List<Alumno> alumnos = alumnoRepositorio.findByDocente(docente);
        return ResponseEntity.ok(alumnos);
    }
}
