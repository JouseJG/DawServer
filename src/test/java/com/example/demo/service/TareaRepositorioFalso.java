package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.memoria.MemoriaProyecto;
import com.example.demo.model.Tarea;
import com.example.demo.repository.TareaRepository;

public class TareaRepositorioFalso extends TareaRepository {
    private final List<Tarea> tareas = new ArrayList<>();
    private int siguienteId = 1;

    public TareaRepositorioFalso() {
        super(new MemoriaProyecto());
    }

    public void con(Tarea... iniciales) {
        tareas.clear();
        tareas.addAll(List.of(iniciales));
        for (Tarea tarea : tareas) {
            if (tarea.getId() == 0) {
                tarea.setId(siguienteId++);
            }
        }
    }

    @Override
    public Boolean proyectExist(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getProyectoId() != null && tarea.getProyectoId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Tarea> getTareas(Boolean completada) {
        List<Tarea> filtradas = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (completada == null || tarea.isCompletada() == completada) {
                filtradas.add(tarea);
            }
        }
        return filtradas;
    }

    @Override
    public Tarea getTareaById(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return tarea;
            }
        }
        return null;
    }

    @Override
    public Tarea save(Tarea tarea) {
        tarea.setId(siguienteId);
        siguienteId++;
        tareas.add(tarea);
        return tarea;
    }

    @Override
    public Tarea update(Tarea tarea) {
        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == tarea.getId()) {
                tareas.set(i, tarea);
                return tarea;
            }
        }
        return null;
    }

    @Override
    public void delete(int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
    }

    @Override
    public List<Tarea> getTareasDelProyecto(int proyectoId) {
        List<Tarea> delProyecto = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea.getProyectoId() != null && tarea.getProyectoId() == proyectoId) {
                delProyecto.add(tarea);
            }
        }
        return delProyecto;
    }
}
