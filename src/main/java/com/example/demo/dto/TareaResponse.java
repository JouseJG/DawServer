package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.model.Tarea;

public record TareaResponse(
        int id,
        String titulo,
        String prioridad,
        boolean completada,
        int proyectoId,
        List<String> etiquetas,
        LocalDate vencimiento,
        ResponsableResponse responsable) {

    public static TareaResponse desde(Tarea tarea) {
        return new TareaResponse(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getPrioridad(),
                tarea.isCompletada(),
                tarea.getProyectoId(),
                tarea.getEtiquetas(),
                tarea.getVencimiento(),
                ResponsableResponse.desde(tarea.getResponsable()));
    }
}