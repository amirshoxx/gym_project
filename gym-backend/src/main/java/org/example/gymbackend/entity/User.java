package org.example.gymbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid default gen_random_uuid()")
    private UUID id;
    private Long chatId;
    private String fullName;
    private String phoneNumber;
    private String password;
    private String image;
    private Status status;
    @ManyToMany(fetch = FetchType.EAGER)
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

    public User(String fullName, String phoneNumber, String password, String image, List<Role> roles) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.image = image;
        this.roles = roles;
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
        return phoneNumber;

    }
    @Override
    public String getPassword() {
        return password;
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
