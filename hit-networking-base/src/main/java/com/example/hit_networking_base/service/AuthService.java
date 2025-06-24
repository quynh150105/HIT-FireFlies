package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.request.AuthRequest;
import com.example.hit_networking_base.domain.dto.response.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(AuthRequest authRequest);
}
