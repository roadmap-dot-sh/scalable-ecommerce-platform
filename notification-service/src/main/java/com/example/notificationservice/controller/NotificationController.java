package com.example.notificationservice.controller;

import com.example.notificationservice.dto.request.PushNotificationRequest;
import com.example.notificationservice.dto.response.NotificationResponse;
import com.example.notificationservice.service.NotificationService;
import com.example.notificationservice.service.PushNotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final PushNotificationService pushNotificationService;

    @GetMapping
    public List<NotificationResponse> recent() {
        return notificationService.listRecent();
    }

    @PostMapping("/push")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void push(@Valid @RequestBody PushNotificationRequest request) {
        pushNotificationService.send(request);
    }

    @PostMapping("/events/order-paid")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void orderPaid(@RequestBody Map<String, String> payload) {
        String orderId = payload.getOrDefault("orderId", "unknown");
        String email = payload.getOrDefault("email", "customer@example.com");
        notificationService.notifyOrderPaid(orderId, email, payload);
    }
}
