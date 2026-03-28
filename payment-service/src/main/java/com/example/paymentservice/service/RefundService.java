package com.example.paymentservice.service;

import com.example.paymentservice.dto.request.RefundRequest;
import com.example.paymentservice.dto.response.RefundResponse;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.entity.Refund;
import com.example.paymentservice.exception.PaymentNotFoundException;
import com.example.paymentservice.model.PaymentStatus;
import com.example.paymentservice.repository.PaymentRepository;
import com.example.paymentservice.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundRepository refundRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public RefundResponse refund(RefundRequest request) {
        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

        if (payment.getStatus() != PaymentStatus.SUCCEEDED) {
            throw new IllegalArgumentException("Only succeeded payments can be refunded");
        }

        BigDecimal refundAmount = request.getAmount();
        if (refundAmount.compareTo(payment.getAmount()) > 0) {
            throw new IllegalArgumentException("Refund exceeds payment amount");
        }

        Refund refund = Refund.builder()
                .paymentId(payment.getId())
                .amount(refundAmount)
                .reason(request.getReason())
                .status("COMPLETED")
                .build();
        refund = refundRepository.save(refund);

        return RefundResponse.builder()
                .id(refund.getId())
                .paymentId(refund.getPaymentId())
                .amount(refund.getAmount())
                .reason(refund.getReason())
                .status(refund.getStatus())
                .createdAt(refund.getCreatedAt())
                .build();
    }
}
