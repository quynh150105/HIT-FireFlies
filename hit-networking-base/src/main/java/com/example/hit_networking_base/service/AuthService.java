package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.request.AuthRequest;
import com.example.hit_networking_base.domain.dto.request.ResetPasswordRequest;
import com.example.hit_networking_base.domain.dto.response.AuthResponseDTO;
import com.example.hit_networking_base.domain.dto.response.ResetPasswordResponseDTO;

public interface AuthService {
    AuthResponseDTO login(AuthRequest authRequest);
    ResetPasswordResponseDTO resetPassword(ResetPasswordRequest request);
}
