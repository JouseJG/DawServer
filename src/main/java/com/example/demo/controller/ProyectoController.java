package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.memoria.MemoriaProyecto;
import com.example.demo.model.Proyecto;
import com.example.demo.model.Tarea;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final List<Proyecto> proyectos;
    private final List<Tarea> tareas;

    private int siguienteId = 1;

    public ProyectoController(MemoriaProyecto memoria) {
        this.proyectos = memoria.getProyectos();
        this.tareas = memoria.getTareas();
    }

    @GetMapping
    public ResponseEntity<List<Proyecto>> lista(@RequestParam(name = "activo", required = false) Boolean activo) {
        if(activo == null){
            return ResponseEntity.ok(proyectos);
        }

        List<Proyecto> filtrado = new ArrayList<>(); 
        for (Proyecto proyecto : proyectos) {
            if (activo == proyecto.getActivo()) {
                filtrado.add(proyecto);
            }
        }
        return ResponseEntity.ok(filtrado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> ficha_proyecto(@PathVariable(name = "id") int id){
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return ResponseEntity.ok(proyecto);
            }
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
    public ResponseEntity<List<Tarea>> tareasDelProyecto(@PathVariable(name = "id") int id) {
        boolean existe = false;
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            return ResponseEntity.notFound().build();
        }

        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea.getProyectoId() == id) {
                resultado.add(tarea);
            }
        }
        return ResponseEntity.ok(resultado);
    }

    @PostMapping
    public ResponseEntity<Proyecto> crear(@RequestBody Proyecto proyecto) {
        proyecto.setId(siguienteId);
        siguienteId += 1;
        proyectos.add(proyecto);
        return ResponseEntity.ok(proyecto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizar(
        @PathVariable(name = "id") int id,
        @RequestBody Proyecto datos) {
            
        for (int i = 0; i < proyectos.size(); i++) {
            if (proyectos.get(i).getId() == id) {
                datos.setId(id);
                proyectos.set(i, datos);
                return ResponseEntity.ok(datos);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        proyectos.removeIf(proyecto -> proyecto.getId() == id);
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