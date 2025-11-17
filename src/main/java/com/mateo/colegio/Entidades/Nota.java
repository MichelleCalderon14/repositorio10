// src/main/java/com/mateo/colegio/Entidades/Nota.java
package com.mateo.colegio.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNota;

    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Docente docente;

    private String materia;
    private Double parcial1;
    private Double parcial2;
    private Double examenFinal;
}
