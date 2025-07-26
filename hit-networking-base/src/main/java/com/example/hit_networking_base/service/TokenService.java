package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.dto.response.UserExportDTO;

public interface TokenService {
    String generateToken(UserExportDTO user, String tokenPass);
    String generateRefreshToken(UserExportDTO user);
    String generateTokenEmail(UserExportDTO userExportDTO);
    boolean verifyToken(String token);
    boolean verifyTokenRefresh(String token);
    String extractUsername(String token);
    String extractRole(String token);
}
