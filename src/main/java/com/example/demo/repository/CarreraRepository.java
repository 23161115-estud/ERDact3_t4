package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Carrera;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    Optional<Carrera> findByNombreIgnoreCase(String nombre);
}
