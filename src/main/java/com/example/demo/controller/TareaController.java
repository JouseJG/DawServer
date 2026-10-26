package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dto.TareaPatchRequest;
import com.example.demo.dto.TareaRequest;
import com.example.demo.dto.TareaResponse;
import com.example.demo.error.RecursoNoEncontradoException;
import com.example.demo.mapper.TareaMapper;
import com.example.demo.memoria.MemoriaProyecto;
import com.example.demo.model.Tarea;
import com.example.demo.service.TareaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(MemoriaProyecto memoria) {
        this.tareaService = new TareaService(memoria);
    }

    @GetMapping
    public List<TareaResponse> lista(
        @RequestParam(name = "completada", required = false) Boolean completada) {
        List<Tarea> seleccion = tareaService.getTareas(completada);
        return TareaMapper.aRespuestas(seleccion);
    }

    @GetMapping("/{id}")
    public TareaResponse detalle(@PathVariable int id) {
        Tarea tarea = tareaService.getTareaById(id);
        if (tarea != null) {
            return TareaMapper.aRespuesta(tarea);
        }
        // return ResponseEntity.notFound().build();
        throw new RecursoNoEncontradoException("tarea", id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<TareaResponse> crear(@Valid @RequestBody  TareaRequest peticion) {
        if (!tareaService.proyectExist(peticion.getProyectoId())) {
            return ResponseEntity.notFound().build();
        }

        Tarea tarea =  tareaService.save(TareaMapper.aModelo(peticion));

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarea.getId())
                .toUri();
        return ResponseEntity.created(ubicacion).body(TareaMapper.aRespuesta(tarea));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(
        @PathVariable(name = "id") int id,
        @RequestBody Tarea datos) {
        Tarea tarea = tareaService.getTareaById(id);
        tarea = tareaService.update(tarea, datos);

        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        tareaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/diagnostico")
    public ResponseEntity<String> diagnostico(
            @RequestHeader(name = "User-Agent") String cliente,
            @RequestHeader(name = "Accept") String acepta) {

        return ResponseEntity.ok("Me llama: " + cliente + "\nQuiere recibir: " + acepta);
    }
    
    @PostMapping("/espejo")
    public ResponseEntity<Tarea> espejo(@RequestBody Tarea tarea) {
        System.out.println("He recibido: " + tarea.getTitulo()
                + " / " + tarea.getPrioridad()
                + " / completada=" + tarea.isCompletada());
        return ResponseEntity.ok(tarea);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TareaResponse> modificar(
            @PathVariable(name = "id") int id,
            @RequestBody TareaPatchRequest cambios) {
        Tarea tarea = tareaService.modificar(id, cambios);
        if (tarea != null) {
            return ResponseEntity.ok(TareaResponse.desde(tarea));
        }
        return ResponseEntity.notFound().build();
    }
}