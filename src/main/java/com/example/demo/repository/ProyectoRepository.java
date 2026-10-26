package com.example.demo.repository;

import java.util.List;

import com.example.demo.memoria.MemoriaProyecto;
import com.example.demo.model.Proyecto;


public class ProyectoRepository {
    private final List<Proyecto> proyectos;
    private int siguienteId = 1;
    
    public ProyectoRepository(MemoriaProyecto memoria) {
        this.proyectos = memoria.getProyectos();
    }

    public boolean existeProyectoConNombre(String nombre) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }
    public Proyecto getProyectoById(int id) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return proyecto;
            }
        }
        return null;
    }
    public List<Proyecto> getProyectos(Boolean activo) {
        if (activo == null) {
            return proyectos;
        }

        return proyectos.stream()
                .filter(proyecto -> proyecto.getActivo() == activo)
                .toList();
    }
    public Proyecto save(Proyecto proyecto) {
        proyecto.setId(siguienteId);
        siguienteId += 1;
        proyectos.add(proyecto);
        return proyecto;
    }
    public Proyecto update(Proyecto proyecto) {
        for (int i = 0; i < proyectos.size(); i++) {
            if (proyectos.get(i).getId() == proyecto.getId()) {
                proyectos.set(i, proyecto);
                return proyecto;
            }
        }
        return null;
    }

    public void delete(int id) {
        proyectos.removeIf(proyecto -> proyecto.getId() == id);
    }

    
}
