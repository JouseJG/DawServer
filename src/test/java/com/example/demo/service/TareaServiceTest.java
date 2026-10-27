package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.error.ReglaDeNegocioException;
import com.example.demo.model.Proyecto;
import com.example.demo.model.Tarea;

class TareaServiceTest {

    @Test
    @DisplayName("Una tarea nueva nace sin completar")
    void unaTareaNuevaNaceSinCompletar() {
        ProyectoRepositorioFalso proyectos = new ProyectoRepositorioFalso();
        proyectos.con(new Proyecto(7, 0, "Web corporativa", "", true));

        TareaService servicio = new TareaService(new TareaRepositorioFalso(), proyectos);

        Tarea entrada = new Tarea();
        entrada.setTitulo("Revisar el login");
        entrada.setProyectoId(7);
        entrada.setCompletada(true);

        Tarea creada = servicio.save(entrada);

        assertFalse(creada.isCompletada());
    }

    @Test
    @DisplayName("No se puede guardar una tarea en un proyecto inexistente")
    void noSePuedeGuardarUnaTareaEnUnProyectoInexistente() {
        ProyectoRepositorioFalso proyectos = new ProyectoRepositorioFalso();
        TareaService servicio = new TareaService(new TareaRepositorioFalso(), proyectos);

        Tarea entrada = new Tarea();
        entrada.setTitulo("Revisar el login");
        entrada.setProyectoId(999);

        ReglaDeNegocioException error = assertThrows(
                ReglaDeNegocioException.class,
                () -> servicio.save(entrada)
        );

        assertEquals("No existe el proyecto 999", error.getMessage());
    }

    @Test
    @DisplayName("No se puede guardar una tarea en un proyecto inactivo")
    void noSePuedeGuardarUnaTareaEnUnProyectoInactivo() {
        ProyectoRepositorioFalso proyectos = new ProyectoRepositorioFalso();
        proyectos.con(new Proyecto(8, 0, "Proyecto archivado", "", false));

        TareaService servicio = new TareaService(new TareaRepositorioFalso(), proyectos);

        Tarea entrada = new Tarea();
        entrada.setTitulo("Revisar el login");
        entrada.setProyectoId(8);

        ReglaDeNegocioException error = assertThrows(
                ReglaDeNegocioException.class,
                () -> servicio.save(entrada)
        );

        assertEquals("El proyecto 8 está inactivo y no admite tareas nuevas", error.getMessage());
    }
}
