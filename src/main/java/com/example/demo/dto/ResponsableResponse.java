package com.example.demo.dto;

import com.example.demo.model.Responsable;

public record ResponsableResponse(String nombre, String email) {
    public static ResponsableResponse desde(Responsable responsable) {
        if (responsable == null) {
            return null;
        }
        return new ResponsableResponse(
                responsable.getNombre(), responsable.getEmail());
    }
}