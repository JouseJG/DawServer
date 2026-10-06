package com.example.demo.controller;

import java.net.URI;
import java.util.ArrayList;
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
import com.example.demo.mapper.TareaMapper;
import com.example.demo.memoria.MemoriaProyecto;
import com.example.demo.model.Proyecto;
import com.example.demo.model.Tarea;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final List<Tarea> tareas;
    private final List<Proyecto> proyectos;
    private int siguienteId = 1;

    public TareaController(MemoriaProyecto memoria) {
        this.tareas = memoria.getTareas();
        this.proyectos = memoria.getProyectos();
    }
    private Boolean proyectExist(int id){
        boolean proyectoExiste = false;
        for(Proyecto proyecto: proyectos){
            if(id == proyecto.getId()){
                proyectoExiste = true;
            }
        }
        return proyectoExiste;
    }
    @GetMapping
    public List<TareaResponse> lista(
        @RequestParam(name = "completada", required = false) Boolean completada) {
        List<Tarea> seleccion = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (completada == null || tarea.isCompletada() == completada) {
                seleccion.add(tarea);
            }
        }
        return TareaMapper.aRespuestas(seleccion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaResponse> detalle(@PathVariable int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return ResponseEntity.ok(TareaMapper.aRespuesta(tarea));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<TareaResponse> crear(@RequestBody  TareaRequest peticion) {
        Tarea tarea = TareaMapper.aModelo(peticion);
        
        if (!proyectExist(tarea.getProyectoId())) {
            return ResponseEntity.notFound().build();
        }

        tarea.setId(siguienteId);
        siguienteId += 1;
        tarea.setTitulo(peticion.getTitulo());
        tarea.setPrioridad(peticion.getPrioridad());
        tarea.setCompletada(false);
        if (peticion.getProyectoId() != null) {
            tarea.setProyectoId(peticion.getProyectoId());
        }

        tareas.add(tarea);

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

        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == id) {
                datos.setId(id);
                datos.setProyectoId(tareas.get(i).getProyectoId());
                
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
    public ResponseEntity<TareaResponse> modificar(
            @PathVariable(name = "id") int id,
            @RequestBody TareaPatchRequest cambios) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                if (cambios.getTitulo() != null) {
                    tarea.setTitulo(cambios.getTitulo());
                }
                if (cambios.getPrioridad() != null) {
                    tarea.setPrioridad(cambios.getPrioridad());
                }
                if (cambios.getCompletada() != null) {
                    tarea.setCompletada(cambios.getCompletada());
                }
                return ResponseEntity.ok(TareaResponse.desde(tarea));
            }
        }
        return ResponseEntity.notFound().build();
    }
}