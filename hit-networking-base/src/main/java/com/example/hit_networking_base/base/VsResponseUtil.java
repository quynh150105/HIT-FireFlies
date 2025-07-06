package com.example.hit_networking_base.base;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class VsResponseUtil {
    public static <T> ResponseEntity<RestData<T>> success(T data){
        return new ResponseEntity<>(RestData.success(data), HttpStatus.OK);
    }

    public static <T>ResponseEntity<RestData<T>> success(HttpStatus status, T data){
        RestData<T> response = new RestData<>(data);
        return new ResponseEntity<>(response, status);
    }

    public static <T>ResponseEntity<RestData<T>> success(MultiValueMap<String, String> header, T data){
        RestData<T> response = new RestData<>(data);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(header);
        return ResponseEntity.ok().headers(httpHeaders).body(response);
    }

    public static <T>ResponseEntity<RestData<T>> error(HttpStatus status, String message){
        RestData<T> response = RestData.error(message);
        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<RestData<T>> success(String message){
        return new ResponseEntity<>(RestData.success(message), HttpStatus.OK);
    }

    public static <T> ResponseEntity<RestData<T>> error(String message){
        return new ResponseEntity<>(RestData.success(message), HttpStatus.BAD_REQUEST);
    }
}
