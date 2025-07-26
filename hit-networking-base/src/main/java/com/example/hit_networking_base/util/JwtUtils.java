package com.example.hit_networking_base.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.time.Instant;

public class JwtUtils {

    public static Claims getClaims(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String extractUsername(String token, String secretKey) {
        return getClaims(token, secretKey).getSubject();
    }

    public static String extractRole(String token, String secretKey) {
        return (String) getClaims(token, secretKey).get("role");
    }

    public static String getTokenPass(String token, String secretKey) {
        return getClaims(token, secretKey).get("sessionId", String.class);
    }
}
