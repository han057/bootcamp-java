package com.example.demo.controller;

import com.example.demo.exceptions.EntidadNoEncontradaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorErroresController {

    @ExceptionHandler(EntidadNoEncontradaException.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(404).body("Error: " + e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e){
        return ResponseEntity.status(500).body("Error: error interno, contacte a soporte");
    }
}
