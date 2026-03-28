package com.example.notificationservice.service;

import com.example.notificationservice.dto.request.EmailRequest;
import com.example.notificationservice.entity.EmailLog;
import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.repository.EmailLogRepository;
import com.example.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final EmailLogRepository emailLogRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void send(EmailRequest request) {
        log.info("[EMAIL] to={} subject={}", request.getTo(), request.getSubject());
        emailLogRepository.save(EmailLog.builder()
                .toAddress(request.getTo())
                .subject(request.getSubject())
                .body(request.getBody())
                .status("SENT")
                .build());
        notificationRepository.save(Notification.builder()
                .channel("EMAIL")
                .recipient(request.getTo())
                .subject(request.getSubject())
                .body(request.getBody())
                .status("SENT")
                .build());
    }
}
