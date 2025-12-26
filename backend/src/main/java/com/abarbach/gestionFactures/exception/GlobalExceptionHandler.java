package com.abarbach.gestionFactures.exception;

import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()){
            errors.putIfAbsent(error.getField(),error.getDefaultMessage());
        }
        return errors;
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String,String>> handleNotFoundExceptions(ResponseStatusException ex) {
        Map<String,String> error = new HashMap<>();
        error.put("error",ex.getReason());
        return new ResponseEntity<>(error,ex.getStatusCode());

    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleExceptions(Exception ex) {
        // AFFICHER L'ERREUR DANS LA CONSOLE D'INTELLIJ (Très important)
        ex.printStackTrace();

        Map<String, String> error = new HashMap<>();
        // RENVOYER LE VRAI MESSAGE D'ERREUR AU LIEU DU MESSAGE GÉNÉRIQUE
        error.put("error", ex.getMessage());
        error.put("cause", ex.getClass().getName()); // Pour savoir quel type d'exception est levé
        return error;
    }

    }
