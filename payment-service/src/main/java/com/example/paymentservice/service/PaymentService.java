package com.example.paymentservice.service;

import com.example.paymentservice.dto.request.PaymentRequest;
import com.example.paymentservice.dto.response.PaymentResponse;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.entity.TransactionLog;
import com.example.paymentservice.exception.PaymentFailedException;
import com.example.paymentservice.exception.PaymentNotFoundException;
import com.example.paymentservice.gateway.PaymentGateway;
import com.example.paymentservice.model.PaymentStatus;
import com.example.paymentservice.repository.PaymentRepository;
import com.example.paymentservice.repository.TransactionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentGatewayService paymentGatewayService;
    private final TransactionLogRepository transactionLogRepository;

    @Transactional
    public PaymentResponse charge(PaymentRequest request) {
        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .currency(request.getCurrency() != null ? request.getCurrency() : "USD")
                .status(PaymentStatus.PENDING)
                .provider(request.getProvider() != null ? request.getProvider() : "STRIPE")
                .build();
        payment = paymentRepository.save(payment);

        PaymentGateway.GatewayChargeResult result = paymentGatewayService.charge(
                payment.getProvider(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getOrderId());

        logTx(payment.getId(), "Charge attempt: " + result.message());

        if (!result.success()) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            throw new PaymentFailedException(result.message());
        }

        payment.setStatus(PaymentStatus.SUCCEEDED);
        payment.setExternalTransactionId(result.externalId());
        payment = paymentRepository.save(payment);
        return toResponse(payment);
    }

    public PaymentResponse getById(String id) {
        return paymentRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
    }

    public PaymentResponse getLatestForOrder(String orderId) {
        return paymentRepository.findFirstByOrderIdOrderByCreatedAtDesc(orderId)
                .map(this::toResponse)
                .orElseThrow(() -> new PaymentNotFoundException("No payment for order"));
    }

    private void logTx(String paymentId, String msg) {
        transactionLogRepository.save(TransactionLog.builder()
                .paymentId(paymentId)
                .message(msg)
                .build());
    }

    private PaymentResponse toResponse(Payment p) {
        return PaymentResponse.builder()
                .id(p.getId())
                .orderId(p.getOrderId())
                .amount(p.getAmount())
                .currency(p.getCurrency())
                .status(p.getStatus())
                .provider(p.getProvider())
                .externalTransactionId(p.getExternalTransactionId())
                .createdAt(p.getCreatedAt())
                .build();
    }
}
