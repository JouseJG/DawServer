package com.example.demo.model;

// Clase tradicional (más de 20 líneas de código repetitivo solo para transportar datos):
public class Responsable {
    private final String nombre;
    private final String email;

    public Responsable(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
}