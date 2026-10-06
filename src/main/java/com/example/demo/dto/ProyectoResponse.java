package com.example.demo.dto;

import com.example.demo.model.Proyecto;

public record ProyectoResponse(
        int id,
        int numeroDeIncidencias,
        String nombre,
        String descripcion,
        boolean activo) {

    public static ProyectoResponse desde(Proyecto proyecto) {
        return new ProyectoResponse(
                proyecto.getId(),
                proyecto.getNumeroDeIncidencias(),
                proyecto.getNombre(),
                proyecto.getDescripcion(),
                proyecto.getActivo()
        );
    }
}