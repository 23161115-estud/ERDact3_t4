package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AlumnoDTO;
import com.example.demo.service.AlumnoService;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoRestController {

    private final AlumnoService alumnoService;

    public AlumnoRestController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public List<AlumnoDTO> listar() {
        return alumnoService.listar();
    }

    @GetMapping("/{id}")
    public AlumnoDTO obtener(@PathVariable("id") Long id) {
        return alumnoService.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlumnoDTO crear(@RequestBody AlumnoDTO alumno) {
        return alumnoService.crear(alumno);
    }

    @PutMapping("/{id}")
    public AlumnoDTO actualizar(@PathVariable("id") Long id, @RequestBody AlumnoDTO alumno) {
        return alumnoService.actualizar(id, alumno);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") Long id) {
        alumnoService.eliminar(id);
    }
}
