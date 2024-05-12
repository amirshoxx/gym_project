package org.example.gymbackend.config;

import org.example.gymbackend.entity.Role;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.RoleRepo;
import org.example.gymbackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {

        List<Role> all = roleRepo.findAll();
        if (all.isEmpty()) {
             roleRepo.saveAll(
                    List.of(new Role("ROLE_USER"),
                            new Role("ROLE_ADMIN"),
                            new Role("ROLE_SUPER_ADMIN"))
            );
            List<Role> roleSuperAdmin = roleRepo.findAllByName("ROLE_SUPER_ADMIN");
            List<Role> roleAdmin = roleRepo.findAllByName("ROLE_ADMIN");
            userRepo.saveAll(Arrays.asList(
                    new User("Annayev Istam","+998903405029", passwordEncoder.encode("123"),  "a11fd266-fa31-4fa8-876b-cf3fe80925783. Леопард.jpg",roleSuperAdmin),
                    new User("Amirshox Nutfullayev","+998914174470", passwordEncoder.encode("123"),  "a11fd266-fa31-4fa8-876b-cf3fe80925783. Леопард.jpg",roleAdmin)
            ));

        }

        }


    }

