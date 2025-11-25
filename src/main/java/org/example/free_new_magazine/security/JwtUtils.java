package org.example.free_new_magazine.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
@Builder
public class JwtUtils {

    private final String secretKeyWord = UUID.randomUUID().toString();

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600 * 1000);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, generateKey())
                .compact();
    }

    private Key generateKey() {
        return Keys.hmacShaKeyFor(secretKeyWord.getBytes());
    }


    public boolean isValidToken(String token) {
        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Date expiration = body.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getSubject(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getSubject();
        } catch (Exception e) {
            throw new RuntimeException("User not Found");
        }
    }

}