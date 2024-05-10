package org.example.gymbackend.service.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.example.gymbackend.entity.User;

import javax.crypto.SecretKey;

public interface JwtService {

    String generateJwt(String id);
    String generateJwtRefresh(String id);
    SecretKey signInWithKey();
    Jws<Claims> extractJwt(String jwt);

}
