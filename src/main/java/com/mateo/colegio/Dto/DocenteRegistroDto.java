package com.mateo.colegio.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocenteRegistroDto {

    // Datos del docente
    private String cedula;
    private String nombres;
    private String apellidos;
    private String email;

    // Datos del usuario para login
    private String username;
    private String password;
}
