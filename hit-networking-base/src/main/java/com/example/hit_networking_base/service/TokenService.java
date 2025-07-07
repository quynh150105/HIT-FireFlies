package com.example.hit_networking_base.service;

import com.example.hit_networking_base.domain.entity.User;

public interface TokenService {
    String generateToken(User user);
    boolean verifyToken(String token);
    String extractUsername(String token);
    String extractRole(String token);
}
