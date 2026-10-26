package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.memoria.MemoriaProyecto;
import com.example.demo.model.Proyecto;
import com.example.demo.model.Tarea;


public class TareaRepository {
    private final List<Tarea> tareas;
    private final List<Proyecto> proyectos;
    private int siguienteId = 1;

    public TareaRepository(MemoriaProyecto memoria) {
        this.tareas = memoria.getTareas();
        this.proyectos = memoria.getProyectos();
    }

    public Boolean proyectExist(int id){
        boolean proyectoExiste = false;
        for(Proyecto proyecto: proyectos){
            if(id == proyecto.getId()){
                proyectoExiste = true;
            }
        }
        return proyectoExiste;
    }
    public List<Tarea> getTareas(Boolean completada) {
        List<Tarea> tareasFiltradas = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (completada == null || tarea.isCompletada() == completada) {
                tareasFiltradas.add(tarea);
            }
        }
        return tareasFiltradas;
    }
    public Tarea getTareaById(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return tarea;
            }
        }
        return null;
    }

    public Tarea save(Tarea tarea) {
        tarea.setId(siguienteId);
        siguienteId += 1;
        tareas.add(tarea);
        return tarea;
    }
    public Tarea update(Tarea tarea) {
        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == tarea.getId()) {
                tareas.set(i, tarea);
                return tarea;
            }
        }
        return null;
    }
    public void delete(int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
    }

    public List<Tarea> getTareasDelProyecto(int proyectoId) {
        List<Tarea> tareasDelProyecto = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea.getProyectoId() == proyectoId) {
                tareasDelProyecto.add(tarea);
            }
        }
        return tareasDelProyecto;
    }
}
