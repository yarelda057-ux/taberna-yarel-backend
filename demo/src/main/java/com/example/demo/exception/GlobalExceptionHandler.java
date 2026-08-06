package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Esta anotación le dice a Spring que esta clase interceptará excepciones globalmente
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Le indicamos que solo intercepte las excepciones de validación (las que lanza @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDeValidacionDTO> manejarErroresDeValidacion(MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        // Extraemos todos los errores y los metemos en nuestro mapa (campo -> mensaje)
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        // Construimos nuestro DTO limpio
        ErrorDeValidacionDTO errorResponse = new ErrorDeValidacionDTO(
                "Error en la validación de los datos enviados",
                errores
        );

        // Retornamos nuestro DTO con el código 400 Bad Request
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}