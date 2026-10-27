package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.memoria.MemoriaProyecto;
import com.example.demo.model.Proyecto;
import com.example.demo.repository.ProyectoRepository;

public class ProyectoRepositorioFalso extends ProyectoRepository {
    private final List<Proyecto> proyectos = new ArrayList<>();

    public ProyectoRepositorioFalso() {
        super(new MemoriaProyecto());
    }

    public void con(Proyecto... iniciales) {
        proyectos.clear();
        proyectos.addAll(List.of(iniciales));
    }

    @Override
    public boolean existeProyectoConNombre(String nombre) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Proyecto getProyectoById(int id) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return proyecto;
            }
        }
        return null;
    }

    @Override
    public List<Proyecto> getProyectos(Boolean activo) {
        if (activo == null) {
            return new ArrayList<>(proyectos);
        }
        List<Proyecto> filtrados = new ArrayList<>();
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getActivo() == activo) {
                filtrados.add(proyecto);
            }
        }
        return filtrados;
    }

    @Override
    public Proyecto save(Proyecto proyecto) {
        proyectos.add(proyecto);
        return proyecto;
    }

    @Override
    public Proyecto update(Proyecto proyecto) {
        for (int i = 0; i < proyectos.size(); i++) {
            if (proyectos.get(i).getId() == proyecto.getId()) {
                proyectos.set(i, proyecto);
                return proyecto;
            }
        }
        return null;
    }

    @Override
    public void delete(int id) {
        proyectos.removeIf(proyecto -> proyecto.getId() == id);
    }
}
