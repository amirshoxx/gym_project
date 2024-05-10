package org.example.gymbackend.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.gymbackend.entity.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String generateJwt(String id) {

        return Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60*20))
                .issuedAt(new Date())
                .subject(id)
                .signWith(signInWithKey())
                .compact();
    }


    @Override
    public String generateJwtRefresh(String id) {
        return Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60*10))
                .issuedAt(new Date())
                .subject(id)
                .signWith(signInWithKey())
                .compact();
    }

    @Override
    public SecretKey signInWithKey() {
        final String SECRET_KEY = "C5ye2KN1jqFm7vVI6aaX4CxMZSOSTisiX6KxgPfUdIE";
        byte[] decodedSecretKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decodedSecretKey);
    }

    @Override
    public Jws<Claims> extractJwt(String jwt) {
        return Jwts.parser().verifyWith(signInWithKey()).build().parseSignedClaims(jwt);
    }
}
