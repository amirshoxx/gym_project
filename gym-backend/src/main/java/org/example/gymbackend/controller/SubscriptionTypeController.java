package org.example.gymbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymbackend.dto.SubscriptionTypeDto;
import org.example.gymbackend.service.SubscriptionTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/subscriptionType")
@RequiredArgsConstructor
public class SubscriptionTypeController {
    private final SubscriptionTypeService subscriptionTypeService;
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAll() {
        return subscriptionTypeService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody SubscriptionTypeDto dto) {
        return subscriptionTypeService.save(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody SubscriptionTypeDto dto) {
        return subscriptionTypeService.update(id, dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return subscriptionTypeService.deleteItem(id);
    }
}
