package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ProyectoPatchRequest;
import com.example.demo.memoria.MemoriaProyecto;
import com.example.demo.model.Proyecto;
import com.example.demo.repository.ProyectoRepository;

public class ProyectoService {
    private final ProyectoRepository proyectoRepository;

    public ProyectoService(MemoriaProyecto memoria) {
        this.proyectoRepository = new ProyectoRepository(memoria);
    }

    public boolean existeProyectoConNombre(String nombre) {
        return proyectoRepository.existeProyectoConNombre(nombre);
    }

    public List<Proyecto> getProyectos(Boolean activo) {
        return proyectoRepository.getProyectos(activo);
    }
    public Proyecto getProyectoById(int id) {
        return proyectoRepository.getProyectoById(id);
    }
    public Proyecto save(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }
    public Proyecto update(Proyecto proyecto) {
        return proyectoRepository.update(proyecto);
    }
    public Proyecto modificar(Proyecto proyecto, ProyectoPatchRequest cambios) {

        if (cambios.getNumeroDeIncidencias() != null) {
            proyecto.setNumeroDeIncidencias(cambios.getNumeroDeIncidencias());
        }
        if (cambios.getNombre() != null) {
            proyecto.setNombre(cambios.getNombre());
        }
        if (cambios.getDescripcion() != null) {
            proyecto.setDescripcion(cambios.getDescripcion());
        }
        if (cambios.getActivo() != null) {
            proyecto.setActivo(cambios.getActivo());
        }

        proyectoRepository.update(proyecto);
        return proyecto;
    }
    public void delete(int id) {
        proyectoRepository.delete(id);
    }

}
