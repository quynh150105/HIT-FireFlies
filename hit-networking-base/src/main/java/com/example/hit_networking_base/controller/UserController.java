package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.RequestUpdateUserDTO;
import com.example.hit_networking_base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class UserController {
    private final UserService service;

    @PutMapping(UrlConstant.Admin.UPDATE)
    public ResponseEntity<?> updateUser(@Valid @RequestBody RequestUpdateUserDTO request){
       return VsResponseUtil.success(service.updateUser(request));
    }

//    @PostMapping(UrlConstant.Admin.CREATE)
//    public ResponseEntity<?> createUser(@Valid @RequestBody RequestUpdateUserDTO request){
//        return VsResponseUtil.success(service.updateUser(request));
//    }
}
