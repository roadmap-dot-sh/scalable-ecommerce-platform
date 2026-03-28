package com.example.orderservice.service;

import com.example.orderservice.client.CartServiceClient;
import com.example.orderservice.client.PaymentServiceClient;
import com.example.orderservice.client.dto.CartClientDtos;
import com.example.orderservice.client.dto.PaymentClientDtos;
import com.example.orderservice.dto.request.CreateOrderRequest;
import com.example.orderservice.dto.response.OrderResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.exception.OrderCancellationException;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartServiceClient cartServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final OrderValidationService orderValidationService;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponse checkout(String userId, CreateOrderRequest request) {
        CartClientDtos.CartResponse cart = cartServiceClient.getCart(userId);
        orderValidationService.assertCartHasItems(cart);

        Order order = Order.builder()
                .userId(userId)
                .status(OrderStatus.AWAITING_PAYMENT)
                .totalAmount(cart.getSubtotal())
                .shippingName(request.getShippingName())
                .shippingLine1(request.getShippingLine1())
                .shippingCity(request.getShippingCity())
                .shippingPostalCode(request.getShippingPostalCode())
                .shippingCountry(request.getShippingCountry() != null ? request.getShippingCountry() : "US")
                .paymentProvider(request.getPaymentProvider() != null ? request.getPaymentProvider() : "STRIPE")
                .build();

        for (CartClientDtos.CartItem line : cart.getItems()) {
            OrderItem item = OrderItem.builder()
                    .order(order)
                    .productId(line.getProductId())
                    .sku(line.getSku())
                    .productName(line.getProductName())
                    .unitPrice(line.getUnitPrice())
                    .quantity(line.getQuantity())
                    .build();
            order.getItems().add(item);
        }

        order = orderRepository.save(order);

        try {
            PaymentClientDtos.ChargeResponse pay = paymentServiceClient.charge(
                    order.getId(),
                    order.getTotalAmount(),
                    order.getCurrency(),
                    order.getPaymentProvider());
            if (pay == null || pay.getStatus() == null || !"SUCCEEDED".equalsIgnoreCase(pay.getStatus())) {
                throw new IllegalStateException("Payment not succeeded");
            }
        } catch (Exception e) {
            order.setStatus(OrderStatus.PAYMENT_FAILED);
            orderRepository.save(order);
            throw new IllegalStateException("Checkout failed: " + e.getMessage(), e);
        }

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        try {
            cartServiceClient.clearCart(userId);
        } catch (Exception e) {
            // order is paid; cart clear is best-effort
        }

        return orderMapper.toResponse(orderRepository.findDetailedById(order.getId()).orElse(order));
    }

    @Transactional(readOnly = true)
    public OrderResponse getById(String id) {
        Order order = orderRepository.findDetailedById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        return orderMapper.toResponse(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponse> listForUser(String userId, Pageable pageable) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(o -> orderMapper.toResponse(orderRepository.findDetailedById(o.getId()).orElse(o)));
    }

    @Transactional
    public OrderResponse cancel(String orderId, String userId) {
        Order order = orderRepository.findDetailedById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        if (!order.getUserId().equals(userId)) {
            throw new OrderCancellationException("Not your order");
        }
        if (order.getStatus() != OrderStatus.AWAITING_PAYMENT
                && order.getStatus() != OrderStatus.PAYMENT_FAILED
                && order.getStatus() != OrderStatus.PAID) {
            throw new OrderCancellationException("Order cannot be cancelled in status " + order.getStatus());
        }
        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toResponse(orderRepository.save(order));
    }
}
