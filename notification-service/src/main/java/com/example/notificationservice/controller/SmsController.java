package com.example.notificationservice.controller;

import com.example.notificationservice.dto.request.SmsRequest;
import com.example.notificationservice.service.SmsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void send(@Valid @RequestBody SmsRequest request) {
        smsService.send(request);
    }
}
