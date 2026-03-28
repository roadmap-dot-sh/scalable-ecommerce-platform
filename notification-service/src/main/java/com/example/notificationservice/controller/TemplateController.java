package com.example.notificationservice.controller;

import com.example.notificationservice.dto.request.TemplateRequest;
import com.example.notificationservice.dto.response.TemplateResponse;
import com.example.notificationservice.service.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateResponse upsert(@Valid @RequestBody TemplateRequest request) {
        return templateService.createOrUpdate(request);
    }

    @GetMapping
    public List<TemplateResponse> list() {
        return templateService.listAll();
    }

    @GetMapping("/{code}")
    public TemplateResponse get(@PathVariable String code) {
        return templateService.getByCode(code);
    }
}
