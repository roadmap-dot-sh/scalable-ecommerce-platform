package com.example.paymentservice.service;

import com.example.paymentservice.dto.request.PaymentMethodRequest;
import com.example.paymentservice.dto.response.PaymentMethodResponse;
import com.example.paymentservice.entity.PaymentMethod;
import com.example.paymentservice.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public List<PaymentMethodResponse> listByUser(String userId) {
        return paymentMethodRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PaymentMethodResponse create(PaymentMethodRequest request) {
        PaymentMethod pm = PaymentMethod.builder()
                .userId(request.getUserId())
                .brand(request.getBrand())
                .last4(request.getLast4())
                .provider(request.getProvider() != null ? request.getProvider() : "CARD")
                .defaultMethod(request.isDefaultMethod())
                .build();
        return toResponse(paymentMethodRepository.save(pm));
    }

    @Transactional
    public void delete(String id) {
        paymentMethodRepository.deleteById(id);
    }

    private PaymentMethodResponse toResponse(PaymentMethod pm) {
        return PaymentMethodResponse.builder()
                .id(pm.getId())
                .userId(pm.getUserId())
                .brand(pm.getBrand())
                .last4(pm.getLast4())
                .provider(pm.getProvider())
                .defaultMethod(pm.isDefaultMethod())
                .createdAt(pm.getCreatedAt())
                .build();
    }
}
