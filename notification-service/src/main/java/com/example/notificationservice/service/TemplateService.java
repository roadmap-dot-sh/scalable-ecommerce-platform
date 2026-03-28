package com.example.notificationservice.service;

import com.example.notificationservice.dto.request.TemplateRequest;
import com.example.notificationservice.dto.response.TemplateResponse;
import com.example.notificationservice.entity.NotificationTemplate;
import com.example.notificationservice.exception.TemplateNotFoundException;
import com.example.notificationservice.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    public String render(String code, Map<String, String> variables) {
        NotificationTemplate t = templateRepository.findByCode(code)
                .orElseThrow(() -> new TemplateNotFoundException("Template not found: " + code));
        String body = t.getBodyTemplate();
        if (variables != null) {
            for (Map.Entry<String, String> e : variables.entrySet()) {
                body = body.replace("{{" + e.getKey() + "}}", e.getValue() != null ? e.getValue() : "");
            }
        }
        return body;
    }

    public String renderSubject(String code, Map<String, String> variables) {
        NotificationTemplate t = templateRepository.findByCode(code)
                .orElseThrow(() -> new TemplateNotFoundException("Template not found: " + code));
        String sub = t.getSubjectTemplate() != null ? t.getSubjectTemplate() : "";
        if (variables != null) {
            for (Map.Entry<String, String> e : variables.entrySet()) {
                sub = sub.replace("{{" + e.getKey() + "}}", e.getValue() != null ? e.getValue() : "");
            }
        }
        return sub;
    }

    @Transactional
    public TemplateResponse createOrUpdate(TemplateRequest request) {
        NotificationTemplate t = templateRepository.findByCode(request.getCode())
                .orElse(NotificationTemplate.builder().code(request.getCode()).build());
        t.setName(request.getName());
        t.setSubjectTemplate(request.getSubjectTemplate());
        t.setBodyTemplate(request.getBodyTemplate());
        return toResponse(templateRepository.save(t));
    }

    @Transactional(readOnly = true)
    public List<TemplateResponse> listAll() {
        return templateRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TemplateResponse getByCode(String code) {
        return templateRepository.findByCode(code)
                .map(this::toResponse)
                .orElseThrow(() -> new TemplateNotFoundException("Template not found: " + code));
    }

    private TemplateResponse toResponse(NotificationTemplate t) {
        return TemplateResponse.builder()
                .id(t.getId())
                .code(t.getCode())
                .name(t.getName())
                .subjectTemplate(t.getSubjectTemplate())
                .bodyTemplate(t.getBodyTemplate())
                .createdAt(t.getCreatedAt())
                .build();
    }
}
