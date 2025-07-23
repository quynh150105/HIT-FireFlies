package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.config.JwtProperties;
import com.example.hit_networking_base.domain.dto.response.UserExportDTO;
import com.example.hit_networking_base.service.SetCheckTokenService;
import com.example.hit_networking_base.service.TokenService;
import com.example.hit_networking_base.util.JwtUtils;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtProperties jwtProperties;
    private final SetCheckTokenService setCheckTokenService;

    @Override
    public String generateToken(UserExportDTO user) {
        setCheckTokenService.setCheckToken(user);
        System.out.println("Time: " + jwtProperties.getAccessExpirationTime());
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExpirationTime()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }

    @Override
    public boolean verifyToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e){
            System.out.println("Invalid token" + e.getMessage());
            return false;
        }
    }

    @Override
    public String extractUsername(String token) {
        return JwtUtils.extractUsername(token, jwtProperties.getSecret());
    }

    @Override
    public String extractRole(String token) {
        return JwtUtils.extractRole(token, jwtProperties.getSecret());
    }
}
