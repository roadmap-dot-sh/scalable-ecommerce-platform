package com.example.notificationservice.service;

import com.example.notificationservice.dto.request.EmailRequest;
import com.example.notificationservice.dto.response.NotificationResponse;
import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final TemplateService templateService;

    @Transactional(readOnly = true)
    public List<NotificationResponse> listRecent() {
        return notificationRepository.findAll().stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .limit(50)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void notifyOrderPaid(String orderId, String customerEmail, Map<String, String> vars) {
        String body;
        String subject;
        try {
            body = templateService.render("ORDER_CONFIRM", vars);
            subject = templateService.renderSubject("ORDER_CONFIRM", vars);
        } catch (Exception e) {
            body = "Order " + orderId + " confirmed.";
            subject = "Order confirmation";
        }
        EmailRequest req = new EmailRequest();
        req.setTo(customerEmail);
        req.setSubject(subject == null || subject.isBlank() ? "Order " + orderId : subject);
        req.setBody(body);
        emailService.send(req);
    }

    private NotificationResponse toResponse(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId())
                .channel(n.getChannel())
                .recipient(n.getRecipient())
                .subject(n.getSubject())
                .body(n.getBody())
                .status(n.getStatus())
                .createdAt(n.getCreatedAt())
                .build();
    }
}
