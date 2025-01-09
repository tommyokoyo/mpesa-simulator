package com.okoyo.mpesasimulator.mpesasimulator.components;

import com.okoyo.mpesasimulator.mpesasimulator.entities.User;
import com.okoyo.mpesasimulator.mpesasimulator.repositories.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Component
public class JWTUtil {

    private final UserRepository userRepository;
    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;
    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public JWTUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(String subject) {
        return Jwts.builder().signWith(secretKey)
                .subject(subject).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    public String getSubject(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Expired JWT token: " + e.getMessage());
        } catch (JwtException e) {
            throw new RuntimeException("invalid JWT token: " + e.getMessage());
        }
    }

    public boolean checkExpiry(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Expired JWT token: " + e.getMessage());
        } catch (JwtException e) {
            throw new RuntimeException("invalid JWT token: " + e.getMessage());
        }
    }

    public boolean validateToken(String token) {
        Optional<User> savedUser = userRepository.findByUserid(getSubject(token));
        return (!checkExpiry(token) && savedUser.isPresent());
    }
}
