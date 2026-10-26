package com.example.demo.controller;

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

import com.example.demo.dto.ProyectoPatchRequest;
import com.example.demo.dto.ProyectoRequest;
import com.example.demo.dto.TareaResponse;
import com.example.demo.error.ConflictoException;
import com.example.demo.mapper.ProjectoMapper;
import com.example.demo.memoria.MemoriaProyecto;
import com.example.demo.model.Proyecto;
import com.example.demo.model.Tarea;
import com.example.demo.service.ProyectoService;
import com.example.demo.service.TareaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

        private final ProyectoService proyectosService;
        private final TareaService tareasService;

    public ProyectoController(MemoriaProyecto memoria) {
        this.proyectosService = new ProyectoService(memoria);
        this.tareasService = new TareaService(memoria);
    }

    @GetMapping
    public ResponseEntity<List<Proyecto>> lista(@RequestParam(name = "activo", required = false) Boolean activo) {
        return ResponseEntity.ok(proyectosService.getProyectos(activo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> ficha_proyecto(@PathVariable(name = "id") int id){
        Proyecto proyecto = proyectosService.getProyectoById(id);
        if (proyecto != null) {
            return ResponseEntity.ok(proyecto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/incidencias")
    public ResponseEntity<String> incidencias_proyecto(
        @RequestHeader(name="User-Agent") String cliente,
        @PathVariable(name = "id") int id, @RequestParam(name="estado", required=false) String estado){
        return ResponseEntity.ok("Incidencias del proyecto "+id);
    }

    @GetMapping("/{id}/tareas")
    public ResponseEntity<List<TareaResponse>> tareasDelProyecto(@PathVariable(name = "id") int id) {
        if (proyectosService.getProyectoById(id) == null) {
            return ResponseEntity.notFound().build();
        }

        List<Tarea> tareas = tareasService.getTareasDelProyecto(id);
        List<TareaResponse> resultado = new ArrayList<>();
        for (Tarea tarea : tareas) {
            resultado.add(TareaResponse.desde(tarea));
        }
        return ResponseEntity.ok(resultado);
    }

    @PostMapping
    public ResponseEntity<Proyecto> crear(@Valid @RequestBody ProyectoRequest peticion) {
        Proyecto proyecto = ProjectoMapper.aModelo(peticion);
        if (proyectosService.existeProyectoConNombre(proyecto.getNombre())) {
            throw new ConflictoException("Ya existe un proyecto con el nombre: " + peticion.getNombre());
        }
        
        return ResponseEntity.ok(proyectosService.save(proyecto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizar(
        @PathVariable(name = "id") int id,
        @Valid @RequestBody ProyectoRequest peticion) {
        
            Proyecto proyecto = proyectosService.getProyectoById(id);
            if (proyecto == null) {
                return ResponseEntity.notFound().build();
            }
            
            proyecto = ProjectoMapper.aModelo(peticion);
            proyecto.setId(id);
            proyecto = proyectosService.update(proyecto);
            return ResponseEntity.ok(proyecto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarParcial(
        @PathVariable(name = "id") int id,
        @RequestBody ProyectoPatchRequest cambios) {
        Proyecto proyecto = proyectosService.getProyectoById(id);
        if (proyecto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(proyectosService.modificar(proyecto, cambios));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        proyectosService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

/*
** Reto · Predice el JSON **
    {
        id: 7,
        titulo: "Caída del servidor",
        nivel: 3
    }

** Reto · Diagnóstico de tres respuestas **
    1- Te el Content-Type mal configurat, te que revisaro be
    2- pot ser que tinga el nom de les funcions del model mas declarades, te que asegurare que despres de escriure  get o set tinga el nombre de les variables amb el primer nombre en mayuscula. example "getNombre" en lugar de "getnombre". 
    3- pot ser que el POST no este guardant les dades en la DB, te que revisar la funcion y asegurase de que les dades es guarden correctament en la db.
*/