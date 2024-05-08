package org.example.gymbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long chatId;
    private String fullName;
    private String phoneNumber;
    private String password;
    private String image;

    @Enumerated(EnumType.STRING)
    private Status status=Status.START;
    @ManyToMany
    private List<Role> roles;

    public User(Long chatId) {
        this.chatId = chatId;
    }

    public User(String fullName, String phoneNumber, String password, String image) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.image = image;
    }

    public User(String fullName, String phoneNumber, String password, List<Role> roles) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return fullName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
