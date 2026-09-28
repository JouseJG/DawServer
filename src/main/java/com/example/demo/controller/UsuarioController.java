package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @GetMapping
    public ResponseEntity<String> lista(@RequestParam(name = "rol", defaultValue = "todos") String rol) {
        return ResponseEntity.ok("Lista de usuarios con rol " + rol);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> detalle(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok("Ficha del usuario " + id);
    }
}