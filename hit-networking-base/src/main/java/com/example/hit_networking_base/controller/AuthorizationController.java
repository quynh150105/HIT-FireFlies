package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.AuthRequest;
import com.example.hit_networking_base.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RestApiV1
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthService authService;

    @PostMapping(UrlConstant.Authorization.LOGIN)
    public ResponseEntity<?> login(AuthRequest authRequest){
        return VsResponseUtil.success(authService.login(authRequest));
    }
}
