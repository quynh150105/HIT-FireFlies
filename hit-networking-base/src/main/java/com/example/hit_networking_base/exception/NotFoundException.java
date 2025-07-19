package com.example.hit_networking_base.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NotFoundException extends RuntimeException{
    private HttpStatus status;
    private long[] params;

    public NotFoundException(String message){
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }

    public NotFoundException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public NotFoundException(String message, long[] params){
        super(message);
        this.status = HttpStatus.NOT_FOUND;
        this.params = params;
    }

    public NotFoundException(String message, HttpStatus status, long[] params){
        super(message);
        this.status = status;
        this.params = params;
    }
}
