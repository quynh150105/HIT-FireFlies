package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.domain.dto.request.RequestCUDUserDTO;
import com.example.hit_networking_base.domain.dto.response.ApiResponse;
import com.example.hit_networking_base.domain.dto.response.UserResponseDTO;
import com.example.hit_networking_base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {
    private final UserService service;

    @PostMapping("/create/User")
    public ResponseEntity<ApiResponse<?>> createUser(@Valid @RequestBody RequestCUDUserDTO request){
        return ResponseEntity.ok(
                ApiResponse.<UserResponseDTO> builder()
                        .message("create thanh cong")
                        .status(HttpStatus.CREATED)
                        .data(service.createUser(request))
                        .build()
        );
    }
}
