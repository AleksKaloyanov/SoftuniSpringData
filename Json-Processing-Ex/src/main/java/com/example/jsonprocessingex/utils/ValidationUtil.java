package com.example.jsonprocessingex.utils;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public interface ValidationUtil {
    <E> boolean isValid(E entity);
    <E> Set<ConstraintViolation<E>> violation(E entity);

}
