package service.authorize.components;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${credit.jwt.secret}")
    private String secret;
    @Value("${credit.jwt.lifetime}")
    private int lifetime;

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + lifetime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getEmailFromJwt(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
