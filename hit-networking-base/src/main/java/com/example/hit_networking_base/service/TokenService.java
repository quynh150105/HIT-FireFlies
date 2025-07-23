package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.response.UserExportDTO;

public interface TokenService {
    String generateToken(UserExportDTO user);
    boolean verifyToken(String token);
    String extractUsername(String token);
    String extractRole(String token);
}
