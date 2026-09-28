package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.net.URI;

import com.example.demo.model.Tarea;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final List<Tarea> tareas = new ArrayList<>();
    private int siguienteId = 1;

    @GetMapping
    public ResponseEntity<List<Tarea>> lista(@RequestParam(name = "completada", required = false) Boolean completada) {
        if (completada == null){
            return ResponseEntity.ok(tareas);
        }

        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea.isCompletada() == completada) {
                resultado.add(tarea);
            }
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> detalle(@PathVariable(name = "id") int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return ResponseEntity.ok(tarea);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // @PostMapping
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tarea> crear(@RequestBody Tarea tarea) {
        tarea.setId(siguienteId);
        siguienteId += 1;
        tareas.add(tarea);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarea.getId())
                .toUri();
        return ResponseEntity.created(ubicacion).body(tarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(
        @PathVariable(name = "id") int id,
        @RequestBody Tarea datos) {

        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == id) {
                datos.setId(id);
                tareas.set(i, datos);
                return ResponseEntity.ok(tareas.get(i));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
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
    public ResponseEntity<Tarea> modificar(
            @PathVariable(name = "id") int id,
            @RequestBody Tarea cambios) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                if (cambios.getTitulo() != null) {
                    tarea.setTitulo(cambios.getTitulo());
                }
                if (cambios.getPrioridad() != null) {
                    tarea.setPrioridad(cambios.getPrioridad());
                }
                return ResponseEntity.ok(tarea);
            }
        }
        return ResponseEntity.notFound().build();
    }
}