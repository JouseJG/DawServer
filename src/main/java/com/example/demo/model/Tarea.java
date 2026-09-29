package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Tarea {

    private int id;
    private int proyectoId;
    private String titulo;
    private String prioridad;
    private boolean completada;

    @JsonCreator
    public Tarea() {
    }

    public Tarea(int id,int proyectoId, String titulo, String prioridad, boolean completada) {
        this.id = id;
        this.proyectoId = proyectoId;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.completada = completada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Integer proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
}