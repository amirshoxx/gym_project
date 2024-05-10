package org.example.gymbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymbackend.dto.SubscriptionTypeDto;
import org.example.gymbackend.service.SubscriptionTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptionType")
public class SubscriptionTypeController {
  private final SubscriptionTypeService subscriptionTypeService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    private ResponseEntity<?> getAll() {
      return subscriptionTypeService.getAll();
    }
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @PostMapping
   private ResponseEntity<?> save(@RequestBody SubscriptionTypeDto dto) {
       return subscriptionTypeService.save(dto);
   }
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @PutMapping("/{id}")
   private ResponseEntity<?> update(@PathVariable UUID id, @RequestBody SubscriptionTypeDto dto) {
       return subscriptionTypeService.update(id,dto);
   }
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @DeleteMapping("/{id}")
   private ResponseEntity<?> delete(@PathVariable UUID id) {
      return subscriptionTypeService.deleteItem(id);
   }
}
