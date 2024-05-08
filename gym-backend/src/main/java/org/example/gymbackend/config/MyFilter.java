package org.example.gymbackend.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.gymbackend.repository.UserRepo;
import org.example.gymbackend.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class MyFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = request.getHeader("token");
            if (token != null) {
                String id = jwtService.parseToken(token);
                UserDetails users = userRepo.findById(UUID.fromString(id)).orElseThrow();
                UsernamePasswordAuthenticationToken token1 = new UsernamePasswordAuthenticationToken(users.getUsername(),null,users.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token1);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        filterChain.doFilter(request,response);
    }
}
