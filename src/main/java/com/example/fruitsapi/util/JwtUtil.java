package com.example.fruitsapi.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.apache.logging.log4j.util.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;

public class JwtUtil {

    // Gunakan kunci statis yang sama untuk validasi
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("your-256-bit-secret-your-256-bit-secret".getBytes());

    // Generate Token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 Menit
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate Token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateJWTFromCookies(Cookie[] cookies) {
        String jwtToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) { // Cari cookie dengan nama 'jwt'
                    jwtToken = cookie.getValue(); // Ambil nilai dari cookie
                    break;
                }
            }
        }

        return Strings.isEmpty(jwtToken) ? false : validateToken(jwtToken);

    }

    // Extract Username
    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
