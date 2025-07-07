package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.AuthRequest;
import com.example.hit_networking_base.domain.dto.request.ResetPasswordRequest;
import com.example.hit_networking_base.service.AuthService;
import com.example.hit_networking_base.service.JobPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthService authService;
    private final JobPostService jobPostService;

    @PostMapping(UrlConstant.Authorization.LOGIN)
    public ResponseEntity<?> login(AuthRequest authRequest){
        return VsResponseUtil.success(authService.login(authRequest));
    }

    @PostMapping(UrlConstant.Authorization.REST_PASSWORD)
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request){
        return VsResponseUtil.success(authService.resetPassword(request));
    }

    @GetMapping(UrlConstant.Authorization.HOME)
    public ResponseEntity<?> listJobPosts(){
        return VsResponseUtil.success(jobPostService.getAllJobPosts());
    }
}
