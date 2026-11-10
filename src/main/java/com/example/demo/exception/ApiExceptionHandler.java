package com.example.demo.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.controller.AlumnoRestController;
import com.example.demo.controller.CarreraRestController;

@RestControllerAdvice(assignableTypes = { AlumnoRestController.class, CarreraRestController.class })
public class ApiExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> noEncontrado(RecursoNoEncontradoException excepcion) {
        return Map.of("error", excepcion.getMessage());
    }

    @ExceptionHandler(DatosInvalidosException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> datosInvalidos(DatosInvalidosException excepcion) {
        return Map.of("error", excepcion.getMessage());
    }
}
