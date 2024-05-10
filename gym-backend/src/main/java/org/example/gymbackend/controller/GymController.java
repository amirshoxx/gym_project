package org.example.gymbackend.controller;

import org.example.gymbackend.dto.GymDto;
import org.example.gymbackend.entity.Gym;
import org.example.gymbackend.repository.GymRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gym")
public class GymController {
    @Autowired
    GymRepo gymRepo;


    @GetMapping
    public ResponseEntity<?> getGym() {
        List<Gym> all = gymRepo.findAll();
        System.out.println(all);
        return ResponseEntity.ok(all);
    }


    @PostMapping
    public ResponseEntity<?> postGym(@RequestBody GymDto gymDto) {
        System.out.println(gymDto);
        Gym gym = new Gym(
                UUID.randomUUID(),
                gymDto.getName(),
                gymDto.getLocation(),
                new ArrayList<>()

        );
        Gym save = gymRepo.save(gym);
        return ResponseEntity.ok(save);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteGym(@RequestParam UUID id) {
        gymRepo.deleteById(id);
        return ResponseEntity.ok("delete");
    }
}
