package org.example.gymbackend.service.jwt;


import org.example.gymbackend.entity.User;

public interface JwtService {

    String getUserToken(User users);

    String parseToken(String token);
    String getUserRefreshToken(User users);

}
