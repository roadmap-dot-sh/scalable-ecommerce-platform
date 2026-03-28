package com.example.notificationservice.service;

import com.example.notificationservice.dto.request.PushNotificationRequest;
import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PushNotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void send(PushNotificationRequest request) {
        log.info("[PUSH] userId={} title={}", request.getUserId(), request.getTitle());
        notificationRepository.save(Notification.builder()
                .channel("PUSH")
                .recipient(request.getUserId())
                .subject(request.getTitle())
                .body(request.getBody())
                .status("QUEUED")
                .build());
    }
}
