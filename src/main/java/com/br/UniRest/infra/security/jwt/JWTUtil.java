package com.br.UniRest.infra.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    private static final String KEY_SECRET = "CHAVE_SECRETA_DE_32_BYTES_MINIMO_XXXXXX";
    private static final long EXPIRATION_TIME = 86400000;

    private Key getKeySecret() {
        return Keys.hmacShaKeyFor(KEY_SECRET.getBytes());
    }

    public String generateToken(Long userId, String username, String role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getKeySecret(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKeySecret())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKeySecret())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    // Novo método para extrair o "subject" (username) do token
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKeySecret())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Long getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKeySecret())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }
}


