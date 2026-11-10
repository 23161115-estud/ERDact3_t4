package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.AlumnoDTO;
import com.example.demo.exception.DatosInvalidosException;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.service.AlumnoService;
import com.example.demo.service.CarreraService;

@Controller
@RequestMapping("/alumnos")
public class AlumnoViewController {

    private final AlumnoService alumnoService;
    private final CarreraService carreraService;
    private final String nombreProyecto;
    private final String autor;

    public AlumnoViewController(
            AlumnoService alumnoService,
            CarreraService carreraService,
            @Value("${app.nombre-proyecto}") String nombreProyecto,
            @Value("${app.autor}") String autor) {
        this.alumnoService = alumnoService;
        this.carreraService = carreraService;
        this.nombreProyecto = nombreProyecto;
        this.autor = autor;
    }

    @GetMapping
    public String listar(@RequestParam(name = "carreraId", required = false) Long carreraId, Model model) {
        List<AlumnoDTO> alumnos = carreraId == null
                ? alumnoService.listar()
                : alumnoService.listarPorCarrera(carreraId);

        agregarDatosComunes(model);
        model.addAttribute("alumnos", alumnos);
        model.addAttribute("carreras", carreraService.listar());
        model.addAttribute("carreraSeleccionada", carreraId);
        return "alumnos";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        prepararFormulario(model, new AlumnoDTO(), "Nuevo alumno");
        return "alumno-form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        try {
            prepararFormulario(model, alumnoService.obtener(id), "Editar alumno");
            return "alumno-form";
        } catch (RecursoNoEncontradoException excepcion) {
            redirect.addFlashAttribute("error", excepcion.getMessage());
            return "redirect:/alumnos";
        }
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("alumno") AlumnoDTO alumno, Model model, RedirectAttributes redirect) {
        boolean esNuevo = alumno.getId() == null;
        try {
            if (esNuevo) {
                alumnoService.crear(alumno);
            } else {
                alumnoService.actualizar(alumno.getId(), alumno);
            }
            redirect.addFlashAttribute("mensaje", esNuevo ? "Alumno creado correctamente." : "Alumno actualizado correctamente.");
            return "redirect:/alumnos";
        } catch (DatosInvalidosException | RecursoNoEncontradoException excepcion) {
            prepararFormulario(model, alumno, esNuevo ? "Nuevo alumno" : "Editar alumno");
            model.addAttribute("error", excepcion.getMessage());
            return "alumno-form";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes redirect) {
        try {
            alumnoService.eliminar(id);
            redirect.addFlashAttribute("mensaje", "Alumno eliminado correctamente.");
        } catch (RecursoNoEncontradoException excepcion) {
            redirect.addFlashAttribute("error", excepcion.getMessage());
        }
        return "redirect:/alumnos";
    }

    private void prepararFormulario(Model model, AlumnoDTO alumno, String titulo) {
        agregarDatosComunes(model);
        model.addAttribute("alumno", alumno);
        model.addAttribute("titulo", titulo);
        model.addAttribute("carreras", carreraService.listar());
    }

    private void agregarDatosComunes(Model model) {
        model.addAttribute("nombreProyecto", nombreProyecto);
        model.addAttribute("autor", autor);
    }
}
