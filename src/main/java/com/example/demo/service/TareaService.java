package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.TareaPatchRequest;
import com.example.demo.error.ReglaDeNegocioException;
import com.example.demo.memoria.MemoriaProyecto;
import com.example.demo.model.Proyecto;
import com.example.demo.model.Tarea;
import com.example.demo.repository.ProyectoRepository;
import com.example.demo.repository.TareaRepository;

public class TareaService {
    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;

    public TareaService(TareaRepository tareaRepository, ProyectoRepository proyectoRepository) {
        this.tareaRepository = tareaRepository;
        this.proyectoRepository = proyectoRepository;
    }

    public TareaService(MemoriaProyecto memoria) {
        this(new TareaRepository(memoria), new ProyectoRepository(memoria));
    }

    public Boolean proyectExist(int id){
        return tareaRepository.proyectExist(id);
    }
    public List<Tarea> getTareas(Boolean completada) {
        return tareaRepository.getTareas(completada);
    }
    public Tarea getTareaById(int id) {
        return tareaRepository.getTareaById(id);
    }
    public Tarea save(Tarea tarea) {
        Proyecto proyecto = proyectoRepository.getProyectoById(tarea.getProyectoId());
        if (proyecto == null) {
            throw new ReglaDeNegocioException(
                    "No existe el proyecto " + tarea.getProyectoId());
        }
        if (!proyecto.getActivo()) {
            throw new ReglaDeNegocioException(
                    "El proyecto " + tarea.getProyectoId() + " está inactivo y no admite tareas nuevas");
        }
        tarea.setCompletada(false);
        return tareaRepository.save(tarea);
    }
    public Tarea update(Tarea tarea, Tarea datos) {
        Boolean proyectoExist = proyectExist(datos.getProyectoId());
        if (!proyectoExist) {
            return null;
        }
        datos.setId(tarea.getId());
        return tareaRepository.update(datos);
    }
    public void delete(int id) {
        tareaRepository.delete(id);
    }
    public Tarea modificar(int id, TareaPatchRequest cambios) {
        Tarea tarea = getTareaById(id);
        if (tarea == null) {
            return null;
        }

        if (cambios.getTitulo() != null) {
            tarea.setTitulo(cambios.getTitulo());
        }
        if (cambios.getPrioridad() != null) {
            tarea.setPrioridad(cambios.getPrioridad());
        }
        if (cambios.getCompletada() != null) {
            tarea.setCompletada(cambios.getCompletada());
        }

        tareaRepository.update(tarea);
        return tarea;
    }
    public List<Tarea> getTareasDelProyecto(int proyectoId) {
        return tareaRepository.getTareasDelProyecto(proyectoId);
    }
}
