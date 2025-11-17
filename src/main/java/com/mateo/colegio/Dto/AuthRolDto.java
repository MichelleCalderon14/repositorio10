package com.mateo.colegio.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRolDto {
    private Integer id_rol;
    private String nombre;
}
