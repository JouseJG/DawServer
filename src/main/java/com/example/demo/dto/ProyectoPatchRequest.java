package com.example.demo.dto;

public class ProyectoPatchRequest {

    private Integer numeroDeIncidencias;
    private String nombre;
    private String descripcion;
    private Boolean activo;

    public ProyectoPatchRequest() {
    }

    public Integer getNumeroDeIncidencias() {
        return numeroDeIncidencias;
    }

    public void setNumeroDeIncidencias(Integer numeroDeIncidencias) {
        this.numeroDeIncidencias = numeroDeIncidencias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
