package com.mateo.colegio.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDto {
    private Integer id_usuario;
    private String username;
    private String email;
    private boolean activo;
    private AuthRolDto rol;
}
