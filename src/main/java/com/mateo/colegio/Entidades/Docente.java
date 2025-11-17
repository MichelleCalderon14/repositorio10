package com.mateo.colegio.Entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "docente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_docente;

    @NotBlank
    @Size(max = 15)
    @Column(unique = true, nullable = false)
    private String cedula;

    @NotBlank
    @Size(max = 100)
    private String nombres;

    @NotBlank
    @Size(max = 100)
    private String apellidos;

    @Email
    @Size(max = 120)
    private String email;

    // 🔹 NUEVO (opcional)
    @OneToMany(mappedBy = "docente")
    @JsonIgnore       // para no entrar en bucles JSON
    private List<Alumno> alumnos = new ArrayList<>();
}
