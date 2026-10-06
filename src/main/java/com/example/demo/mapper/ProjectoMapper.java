package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.ProyectoPatchRequest;
import com.example.demo.dto.ProyectoRequest;
import com.example.demo.dto.ProyectoResponse;
import com.example.demo.model.Proyecto;

public class ProjectoMapper {

    private ProjectoMapper() {
    }

    /** De lo que envía el cliente a un modelo nuevo. El id lo pone quien llame. */
    public static Proyecto aModelo(ProyectoRequest peticion) {
        Proyecto Proyecto = new Proyecto();
        Proyecto.setNombre(peticion.getNombre());
        Proyecto.setNumeroDeIncidencias(peticion.getNumeroDeIncidencias());
        Proyecto.setDescripcion(peticion.getDescripcion());
        Proyecto.setActivo(peticion.isActivo());
        return Proyecto;
    }

    /** Del modelo a lo que publica la API. */
    public static ProyectoResponse aRespuesta(Proyecto proyecto) {
        return new ProyectoResponse(
            proyecto.getId(),
            proyecto.getNumeroDeIncidencias(),
            proyecto.getNombre(),
            proyecto.getDescripcion(),
            proyecto.getActivo()
        );
    }

    public static List<ProyectoResponse> aRespuestas(List<Proyecto> Proyectos) {
        List<ProyectoResponse> respuesta = new ArrayList<>();
        for (Proyecto Proyecto : Proyectos) {
            respuesta.add(aRespuesta(Proyecto));
        }
        return respuesta;
    }
// numeroDeIncidencias
// nombre
// descripcion
// activo
    /** Aplica sobre una Proyecto existente solo los campos que traiga el cambio. */
    public static void aplicar(ProyectoPatchRequest cambios, Proyecto proyecto) {
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
    }
}