package com.example.hit_networking_base.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handlerValidationException(MethodArgumentNotValidException ex){
        return ResponseEntity.badRequest().body(ex.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handlerUserException(UserException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
