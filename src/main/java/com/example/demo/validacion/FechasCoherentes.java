package com.example.demo.validacion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FechasCoherentesValidator.class)
public @interface FechasCoherentes {

    String message() default "La fecha de fin no puede ser anterior a la de inicio";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}