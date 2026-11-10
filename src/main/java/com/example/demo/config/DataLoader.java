package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Alumno;
import com.example.demo.entity.Carrera;
import com.example.demo.repository.AlumnoRepository;
import com.example.demo.repository.CarreraRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final CarreraRepository carreraRepository;
    private final AlumnoRepository alumnoRepository;

    public DataLoader(CarreraRepository carreraRepository, AlumnoRepository alumnoRepository) {
        this.carreraRepository = carreraRepository;
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    public void run(String... args) {
        if (carreraRepository.count() > 0) {
            return;
        }

        Carrera sistemas = carreraRepository.save(new Carrera("Ingeniería en Sistemas Computacionales"));
        Carrera arquitectura = carreraRepository.save(new Carrera("Arquitectura"));
        carreraRepository.save(new Carrera("Contaduría Pública"));
        carreraRepository.save(new Carrera("Ingeniería Civil"));

        alumnoRepository.save(new Alumno("Ana López Ruiz", "ana.lopez@escuela.mx", 5, sistemas));
        alumnoRepository.save(new Alumno("Luis Ortega Pérez", "luis.ortega@escuela.mx", 3, sistemas));
        alumnoRepository.save(new Alumno("Marta Castillo Vera", "marta.castillo@escuela.mx", 7, arquitectura));
    }
}
