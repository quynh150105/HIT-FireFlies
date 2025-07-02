package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
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

@RequiredArgsConstructor
@RestApiV1
public class UserController {
    private final UserService service;

    @PostMapping(UrlConstant.Admin.CREATE)
    public ResponseEntity<?> createUser(@Valid @RequestBody RequestCUDUserDTO request){
       return VsResponseUtil.success(service.createUser(request));
    }
}
