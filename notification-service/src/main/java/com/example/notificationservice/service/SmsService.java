package com.example.notificationservice.service;

import com.example.notificationservice.dto.request.SmsRequest;
import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.entity.SmsLog;
import com.example.notificationservice.repository.NotificationRepository;
import com.example.notificationservice.repository.SmsLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    private final SmsLogRepository smsLogRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void send(SmsRequest request) {
        log.info("[SMS] to={} body={}", request.getTo(), request.getBody());
        smsLogRepository.save(SmsLog.builder()
                .toNumber(request.getTo())
                .body(request.getBody())
                .status("SENT")
                .build());
        notificationRepository.save(Notification.builder()
                .channel("SMS")
                .recipient(request.getTo())
                .subject(null)
                .body(request.getBody())
                .status("SENT")
                .build());
    }
}
