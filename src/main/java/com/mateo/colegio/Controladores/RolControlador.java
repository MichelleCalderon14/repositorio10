package com.mateo.colegio.Controladores;

import com.mateo.colegio.Entidades.Rol;
import com.mateo.colegio.Servcios.RolServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolControlador {

    private final RolServicio servicio;

    @GetMapping
    public List<Rol> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> porId(@PathVariable Integer id) {
        return servicio.porId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rol> crear(@RequestBody Rol e) {
        return ResponseEntity.ok(servicio.guardar(e));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizar(@PathVariable Integer id, @RequestBody Rol e) {
        e.setIdRol(id);   // ✅ ESTE ES EL CORRECTO
        return ResponseEntity.ok(servicio.guardar(e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
