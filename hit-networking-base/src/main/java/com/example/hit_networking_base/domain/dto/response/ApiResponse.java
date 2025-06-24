package com.example.hit_networking_base.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NotBlank
public class ApiResponse<T> {
    HttpStatus status;

    String message;

    T data;
}
