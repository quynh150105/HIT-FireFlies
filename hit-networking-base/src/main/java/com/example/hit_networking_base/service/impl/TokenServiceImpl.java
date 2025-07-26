package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.config.JwtProperties;
import com.example.hit_networking_base.domain.dto.response.UserExportDTO;
import com.example.hit_networking_base.service.SetCheckTokenService;
import com.example.hit_networking_base.service.TokenService;
import com.example.hit_networking_base.util.GenPassword;
import com.example.hit_networking_base.util.JwtUtils;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtProperties jwtProperties;
    private final SetCheckTokenService setCheckTokenService;
    @Override
    public String generateToken(UserExportDTO user, String tokenPass) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .claim("sessionId", tokenPass)
                .claim("type", "access")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExpirationTime()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }

    @Override
    public String generateRefreshToken(UserExportDTO user) {
        String passToken = GenPassword.generatePassword();
        String encodedSessionId = new BCryptPasswordEncoder(10).encode(passToken);
        setCheckTokenService.setCheckToken(user, encodedSessionId);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .claim("sessionId", passToken)
                .claim("type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpirationTime()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }

    @Override
    public String generateTokenEmail(UserExportDTO userExportDTO) {
        return Jwts.builder()
                .setSubject(userExportDTO.getUsername())
                .claim("role", userExportDTO.getRole())
                .claim("type", "access")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpirationTime()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }

    @Override
    public boolean verifyToken(String token) {
        try {
            Claims claims = Jwts.parser()
                     .setSigningKey(jwtProperties.getSecret())
                     .parseClaimsJws(token)
                     .getBody();
            return "access".equals(claims.get("type"));
        } catch (JwtException e){
            System.out.println("Invalid token" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean verifyTokenRefresh(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
            return "refresh".equals(claims.get("type"));
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
