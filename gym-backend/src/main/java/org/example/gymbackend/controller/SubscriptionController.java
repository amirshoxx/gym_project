package org.example.gymbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymbackend.dto.SubscriptionDto;
import org.example.gymbackend.service.subscriptions.SubscriptionsService;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionsService subscriptionsService;
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpEntity<?> getSubscriptions() {
        return subscriptionsService.getAllSubscriptions();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpEntity<?> search(@RequestParam String phoneNumber) {
        HttpEntity<?> search = subscriptionsService.search(phoneNumber);
        return search;
    }

    @PostMapping("/select/subscription")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpEntity<?> saveSubscription(@RequestBody SubscriptionDto dto, @RequestParam UUID id) {
        HttpEntity<?> httpEntity = subscriptionsService.selectSubject(dto, id);
        return httpEntity;
    }
   @PostMapping
   @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
   public HttpEntity<?> saveTasks(@RequestBody SubscriptionDto dto) {
        return subscriptionsService.save(dto);
   }

   @PutMapping
   @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpEntity<?> updateTasks(@RequestParam UUID id) {
       HttpEntity<?> edite = subscriptionsService.edite(id);
       return edite;
   }
    @PatchMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpEntity<?> editTasks(@RequestParam UUID id) {
        HttpEntity<?> edite = subscriptionsService.patch(id);
        return edite;
    }


}
