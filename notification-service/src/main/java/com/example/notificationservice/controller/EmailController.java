package com.example.notificationservice.controller;

import com.example.notificationservice.dto.request.EmailRequest;
import com.example.notificationservice.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void send(@Valid @RequestBody EmailRequest request) {
        emailService.send(request);
    }
}
