package com.mateo.colegio.docentes;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "docente_perfil")
public class DocentePerfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // IMPORTANTE: este campo es NOT NULL en la BD
    @Column(nullable = false, length = 120, name = "nombre_completo")
    private String nombreCompleto;

    @Column(length = 255, name = "fotografia_url")
    private String fotografiaUrl;

    @Column(length = 80)
    private String cargo;

    @Column(length = 120)
    private String area;

    @Column(name = "anios_experiencia")
    private Integer aniosExperiencia;

    // ==========================
    //  Relaciones opcionales
    //  (si ya tienes estas entidades creadas)
    // ==========================

    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormacionAcademica> formaciones = new ArrayList<>();

    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienciaProfesional> experiencias = new ArrayList<>();

    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reconocimiento> reconocimientos = new ArrayList<>();

    // ===== GETTERS y SETTERS =====

    public Long getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFotografiaUrl() {
        return fotografiaUrl;
    }

    public void setFotografiaUrl(String fotografiaUrl) {
        this.fotografiaUrl = fotografiaUrl;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(Integer aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public List<FormacionAcademica> getFormaciones() {
        return formaciones;
    }

    public void setFormaciones(List<FormacionAcademica> formaciones) {
        this.formaciones = formaciones;
    }

    public List<ExperienciaProfesional> getExperiencias() {
        return experiencias;
    }

    public void setExperiencias(List<ExperienciaProfesional> experiencias) {
        this.experiencias = experiencias;
    }

    public List<Reconocimiento> getReconocimientos() {
        return reconocimientos;
    }

    public void setReconocimientos(List<Reconocimiento> reconocimientos) {
        this.reconocimientos = reconocimientos;
    }
}
