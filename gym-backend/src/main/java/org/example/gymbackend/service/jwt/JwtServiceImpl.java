package org.example.gymbackend.service.jwt;

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
    public String getUserToken(User users) {
        Map<String, String> map = Map.of("username", users.getUsername());

        String compact = Jwts.builder()
                .claims(map)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 10000*10*10))
                .subject(users.getId().toString())
                .signWith(secretKey())
                .compact();

        return compact;
    }

    @Override
    public String parseToken(String token) {
        String subject = Jwts.parser()
                .verifyWith(secretKey()).build().parseSignedClaims(token).getPayload().getSubject();
        return subject;
    }



    @Override
    public String getUserRefreshToken(User users) {
        String compact = Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*10))
                .subject(users.getId().toString())
                .signWith(secretKey())
                .compact();
        return compact;
    }

    private SecretKey secretKey(){
        byte[] decode = Decoders.BASE64.decode("GbQtiaFmi3+DBvp3DL+gXM75wVa9xRASQGAKrkW00sE=");
        return Keys.hmacShaKeyFor(decode);
    }
}
