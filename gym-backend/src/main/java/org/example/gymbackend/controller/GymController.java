package org.example.gymbackend.controller;

import org.example.gymbackend.dto.GymDto;
import org.example.gymbackend.entity.Gym;
import org.example.gymbackend.repository.GymRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGymId(@PathVariable UUID id) {
        List<Gym> allById = gymRepo.findAllById(Collections.singleton(id));
        return ResponseEntity.ok(allById);
    }






    @PostMapping
    public ResponseEntity<?> postGym(@RequestBody GymDto gymDto) {
        Gym gym = new Gym(
                gymDto.getName(),
                gymDto.getLocation()
        );
        Gym save = gymRepo.save(gym);
        return ResponseEntity.ok(save);
    }
}
