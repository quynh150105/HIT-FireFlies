package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.domain.dto.response.UserExportDTO;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.service.TokenService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final String SECRET_KEY = "QebC#BUd)t11aEhHitNetwork";
    private final long EXPIRATION_TIME = 86400000;

    @Override
    public String generateToken(UserExportDTO user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    @Override
    public boolean verifyToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e){
            System.out.println("Invalid token" + e.getMessage());
            return false;
        }
    }

    @Override
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public String extractRole(String token) {
        return (String) getClaims(token).get("role");
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
