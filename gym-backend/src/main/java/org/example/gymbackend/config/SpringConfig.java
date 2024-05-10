package org.example.gymbackend.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringConfig {
    private final MyFilter myFilter;
    private final UserRepo userRepo;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/user/login").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/refresh").permitAll()
                        .requestMatchers("/user","/user/admins","/user/super_admins","/user/admin").permitAll()
                        .requestMatchers("/fileController").permitAll()
                        .requestMatchers("/fileController/{image}").permitAll()
                        .anyRequest().authenticated()
        ).addFilterBefore(myFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }


    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            User user = userRepo.findByPhoneNumber(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not found "));
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    new ArrayList<>()
            );

        };
    }

    @Bean
    public BCryptPasswordEncoder encoder(){return new BCryptPasswordEncoder();}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}