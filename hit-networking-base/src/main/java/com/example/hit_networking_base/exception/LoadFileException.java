package com.example.hit_networking_base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoadFileException extends RuntimeException {
    public LoadFileException(String message){
        super(message);
    }
}
