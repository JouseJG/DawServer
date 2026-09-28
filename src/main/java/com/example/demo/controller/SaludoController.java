package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;

@RestController
public class SaludoController {

    @GetMapping("/saludo")
    public ResponseEntity<String> saludo(
        @RequestParam(name = "nombre", defaultValue="Pepe") String nombre
    ) {
        return ResponseEntity.ok("Hola, " + nombre + ".");
    }

    @GetMapping("/incidencias")
    public ResponseEntity<String> buscar(
            @RequestParam(name = "estado", defaultValue = "todas") String estado,
            @RequestParam(name = "pagina", defaultValue = "1") int pagina) {

        return ResponseEntity.ok("Buscando incidencias con estado " + estado
                + ", página " + pagina);
    }
    @GetMapping("/informes")
    public ResponseEntity<String> informes(
            @RequestParam(name = "desde") LocalDate desde,
            @RequestParam(name = "activo", defaultValue = "true") boolean activo) {

        return ResponseEntity.ok("Desde " + desde + " (día " + desde.getDayOfMonth()
                + " del mes " + desde.getMonthValue() + "), activo=" + activo);
    }
}