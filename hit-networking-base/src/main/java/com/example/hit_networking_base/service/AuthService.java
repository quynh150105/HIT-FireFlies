package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.request.AuthRequest;
import com.example.hit_networking_base.domain.dto.request.ResetPasswordRequest;
import com.example.hit_networking_base.domain.dto.response.AuthResponseDTO;
import com.example.hit_networking_base.domain.dto.response.ResetPasswordResponseDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    AuthResponseDTO login(AuthRequest authRequest, HttpServletResponse response);
    ResetPasswordResponseDTO resetPassword(ResetPasswordRequest request);
    String refreshToken(HttpServletRequest request);
}
