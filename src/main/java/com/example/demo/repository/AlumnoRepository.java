package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    List<Alumno> findByCarreraId(Long carreraId);

    Optional<Alumno> findByCorreoIgnoreCase(String correo);
}
