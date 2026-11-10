package com.example.demo.dto;

public class AlumnoDTO {

    private Long id;
    private String nombre;
    private String correo;
    private Integer semestre;
    private Long carreraId;
    private String carreraNombre;

    public AlumnoDTO() {
    }

    public AlumnoDTO(Long id, String nombre, String correo, Integer semestre, Long carreraId, String carreraNombre) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.semestre = semestre;
        this.carreraId = carreraId;
        this.carreraNombre = carreraNombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Long getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Long carreraId) {
        this.carreraId = carreraId;
    }

    public String getCarreraNombre() {
        return carreraNombre;
    }

    public void setCarreraNombre(String carreraNombre) {
        this.carreraNombre = carreraNombre;
    }
}
