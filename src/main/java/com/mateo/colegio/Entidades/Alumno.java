package com.mateo.colegio.Entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="alumno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Alumno {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id_alumno;

    @NotBlank
    @Size(max=15)
    @Column(unique = true, nullable = false)
    private String cedula;

    @NotBlank
    @Size(max=100)
    private String nombres;

    @NotBlank
    @Size(max=100)
    private String apellidos;

    private LocalDate fecha_nacimiento;

    @Email
    @Size(max=120)
    private String email;

    // 🔹 NUEVO: relación muchos-alumnos / un-docente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_docente")   // columna FK en la tabla alumno
    private Docente docente;
}
