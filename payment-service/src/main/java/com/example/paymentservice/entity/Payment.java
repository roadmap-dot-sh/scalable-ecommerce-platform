package com.example.paymentservice.entity;

import com.example.paymentservice.model.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    private String id;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    private String currency;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String provider;

    private String externalTransactionId;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
