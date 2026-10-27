package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.error.ReglaDeNegocioException;
import com.example.demo.model.Proyecto;

class ProyectoServiceTest {

    @Test
    @DisplayName("Se puede guardar un proyecto con un nombre nuevo")
    void sePuedeGuardarUnProyectoConUnNombreNuevo() {
        ProyectoRepositorioFalso proyectos = new ProyectoRepositorioFalso();
        ProyectoService servicio = new ProyectoService(proyectos);

        Proyecto proyecto = new Proyecto(1, 0, "Portal nuevo", "", true);

        Proyecto guardado = servicio.save(proyecto);

        assertEquals("Portal nuevo", guardado.getNombre());
    }

    @Test
    @DisplayName("No se puede guardar un proyecto con el mismo nombre")
    void noSePuedeGuardarUnProyectoConElMismoNombre() {
        ProyectoRepositorioFalso proyectos = new ProyectoRepositorioFalso();
        proyectos.con(new Proyecto(1, 0, "Portal nuevo", "", true));

        ProyectoService servicio = new ProyectoService(proyectos);

        Proyecto proyecto = new Proyecto(2, 0, "Portal nuevo", "", true);

        ReglaDeNegocioException error = assertThrows(
                ReglaDeNegocioException.class,
                () -> servicio.save(proyecto)
        );

        assertEquals("Ya existe un proyecto con el nombre Portal nuevo", error.getMessage());
    }
}
