package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AlumnoDTO;
import com.example.demo.dto.CarreraDTO;
import com.example.demo.service.AlumnoService;
import com.example.demo.service.CarreraService;

@RestController
@RequestMapping("/api/carreras")
public class CarreraRestController {

    private final CarreraService carreraService;
    private final AlumnoService alumnoService;

    public CarreraRestController(CarreraService carreraService, AlumnoService alumnoService) {
        this.carreraService = carreraService;
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public List<CarreraDTO> listar() {
        return carreraService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarreraDTO crear(@RequestBody CarreraDTO carrera) {
        return carreraService.crear(carrera);
    }

    @GetMapping("/{id}/alumnos")
    public List<AlumnoDTO> alumnosDeLaCarrera(@PathVariable("id") Long id) {
        return alumnoService.listarPorCarrera(id);
    }
}
