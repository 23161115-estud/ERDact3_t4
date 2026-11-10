package com.example.demo.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.CarreraDTO;
import com.example.demo.entity.Carrera;
import com.example.demo.exception.DatosInvalidosException;
import com.example.demo.repository.CarreraRepository;

@Service
public class CarreraService {

    private final CarreraRepository carreraRepository;

    public CarreraService(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    @Transactional(readOnly = true)
    public List<CarreraDTO> listar() {
        return carreraRepository.findAll().stream()
                .sorted(Comparator.comparing(Carrera::getId))
                .map(this::aDTO)
                .toList();
    }

    @Transactional
    public CarreraDTO crear(CarreraDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new DatosInvalidosException("El nombre de la carrera es obligatorio.");
        }
        String nombre = dto.getNombre().trim();
        if (carreraRepository.findByNombreIgnoreCase(nombre).isPresent()) {
            throw new DatosInvalidosException("Ya existe una carrera con ese nombre.");
        }
        return aDTO(carreraRepository.save(new Carrera(nombre)));
    }

    private CarreraDTO aDTO(Carrera carrera) {
        return new CarreraDTO(carrera.getId(), carrera.getNombre(), carrera.getAlumnos().size());
    }
}
