package com.example.demo.dto;

public class CarreraDTO {

    private Long id;
    private String nombre;
    private Integer totalAlumnos;

    public CarreraDTO() {
    }

    public CarreraDTO(Long id, String nombre, Integer totalAlumnos) {
        this.id = id;
        this.nombre = nombre;
        this.totalAlumnos = totalAlumnos;
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

    public Integer getTotalAlumnos() {
        return totalAlumnos;
    }

    public void setTotalAlumnos(Integer totalAlumnos) {
        this.totalAlumnos = totalAlumnos;
    }
}
