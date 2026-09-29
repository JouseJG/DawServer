package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {
    @GetMapping("/hola")
    public ResponseEntity<String> saludo() {
        return ResponseEntity.ok("Hola, mundo. Te responde mi servidor.");
    }

    @GetMapping("/anyo")
    public ResponseEntity<Integer> anyo() {
        return ResponseEntity.ok(2026);
    }

    @GetMapping("/estado")
    public ResponseEntity<String> estado() {
        return ResponseEntity.ok("Servidor en funcionamiento");
    }

    @GetMapping("/prestamos/resumen")
    public ResponseEntity<String> resumen() {
        return ResponseEntity.ok("Esta aplicación gestionará los préstamos de material");
    }
}