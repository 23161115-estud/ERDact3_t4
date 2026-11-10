package com.example.demo.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.AlumnoDTO;
import com.example.demo.entity.Alumno;
import com.example.demo.entity.Carrera;
import com.example.demo.exception.DatosInvalidosException;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.repository.AlumnoRepository;
import com.example.demo.repository.CarreraRepository;

@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final CarreraRepository carreraRepository;

    public AlumnoService(AlumnoRepository alumnoRepository, CarreraRepository carreraRepository) {
        this.alumnoRepository = alumnoRepository;
        this.carreraRepository = carreraRepository;
    }

    @Transactional(readOnly = true)
    public List<AlumnoDTO> listar() {
        return alumnoRepository.findAll().stream()
                .sorted(Comparator.comparing(Alumno::getId))
                .map(this::aDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AlumnoDTO> listarPorCarrera(Long carreraId) {
        if (!carreraRepository.existsById(carreraId)) {
            throw new RecursoNoEncontradoException("No existe una carrera con el id " + carreraId + ".");
        }
        return alumnoRepository.findByCarreraId(carreraId).stream()
                .sorted(Comparator.comparing(Alumno::getId))
                .map(this::aDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public AlumnoDTO obtener(Long id) {
        return aDTO(buscar(id));
    }

    @Transactional
    public AlumnoDTO crear(AlumnoDTO dto) {
        validar(dto, null);
        Carrera carrera = buscarCarrera(dto.getCarreraId());
        Alumno alumno = new Alumno(dto.getNombre().trim(), dto.getCorreo().trim(), dto.getSemestre(), carrera);
        return aDTO(alumnoRepository.save(alumno));
    }

    @Transactional
    public AlumnoDTO actualizar(Long id, AlumnoDTO dto) {
        Alumno alumno = buscar(id);
        validar(dto, id);
        alumno.setNombre(dto.getNombre().trim());
        alumno.setCorreo(dto.getCorreo().trim());
        alumno.setSemestre(dto.getSemestre());
        alumno.setCarrera(buscarCarrera(dto.getCarreraId()));
        return aDTO(alumnoRepository.save(alumno));
    }

    @Transactional
    public void eliminar(Long id) {
        alumnoRepository.delete(buscar(id));
    }

    private Alumno buscar(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un alumno con el id " + id + "."));
    }

    private Carrera buscarCarrera(Long carreraId) {
        return carreraRepository.findById(carreraId)
                .orElseThrow(() -> new DatosInvalidosException("La carrera seleccionada no existe."));
    }

    private void validar(AlumnoDTO dto, Long idActual) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new DatosInvalidosException("El nombre es obligatorio.");
        }
        if (dto.getCorreo() == null || !dto.getCorreo().contains("@")) {
            throw new DatosInvalidosException("Escribe un correo válido.");
        }
        if (dto.getSemestre() == null || dto.getSemestre() < 1 || dto.getSemestre() > 12) {
            throw new DatosInvalidosException("El semestre debe estar entre 1 y 12.");
        }
        if (dto.getCarreraId() == null) {
            throw new DatosInvalidosException("Selecciona una carrera.");
        }
        alumnoRepository.findByCorreoIgnoreCase(dto.getCorreo().trim()).ifPresent(existente -> {
            if (!existente.getId().equals(idActual)) {
                throw new DatosInvalidosException("Ya existe un alumno con ese correo.");
            }
        });
    }

    private AlumnoDTO aDTO(Alumno alumno) {
        return new AlumnoDTO(
                alumno.getId(),
                alumno.getNombre(),
                alumno.getCorreo(),
                alumno.getSemestre(),
                alumno.getCarrera().getId(),
                alumno.getCarrera().getNombre());
    }
}
