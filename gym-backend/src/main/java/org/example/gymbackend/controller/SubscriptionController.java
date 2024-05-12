package org.example.gymbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymbackend.dto.SubscriptionDto;
import org.example.gymbackend.repository.SubscriptionRepo;
import org.example.gymbackend.service.subscriptions.SubscriptionsService;
import org.example.gymbackend.service.user.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionsService subscriptionsService;
    private final UserService userService;

    @GetMapping("/search")
    public HttpEntity<?> search(@RequestParam String phoneNumber) {
        HttpEntity<?> search = userService.search(phoneNumber);
        return search;
    }

   @PostMapping
   public HttpEntity<?> saveTasks(@RequestBody SubscriptionDto dto) {
        return subscriptionsService.save(dto);
   }


}
