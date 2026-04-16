package com.ecocart.backend.controller;

import com.ecocart.backend.entity.Order;
import com.ecocart.backend.service.OrderService;
import com.ecocart.backend.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")

public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Order order) {
        try {
            Order saved = orderService.createOrder(order);
            return ResponseEntity.ok(Map.of("success", true, "message", "Order created successfully", "order", saved));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PostMapping("/checkout/{userId}")
public ResponseEntity<Map<String, Object>> checkout(
        @PathVariable Long userId,
        @RequestBody Map<String, Object> payload) {

    try {
        // STEP 1: (Later) get cart items
        // STEP 2: validate stock
        // STEP 3: apply promo if exists
        // STEP 4: create order
        // STEP 5: process payment
        // STEP 6: generate carbon report
        // STEP 7: clear cart

        Map<String, Object> result = orderService.checkout(userId, payload);
return ResponseEntity.ok(result);

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "success", false,
                        "message", e.getMessage()
                ));
    }
}

    @GetMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderDetails(@PathVariable Long orderId) {
        try {
            Order order = orderService.getOrderDetails(orderId);
            return ResponseEntity.ok(Map.of("success", true, "order", order));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getUserOrders(@PathVariable Long userId) {
        try {
            List<Order> orders = orderRepository.findByUserIdOrderByOrderDateDesc(userId);
            return ResponseEntity.ok(Map.of("success", true, "orders", orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Failed to get user orders"));
        }
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Map<String, Object>> updateOrderStatus(@PathVariable Long orderId, @RequestParam Order.Status status) {
        try {
            Order updated = orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok(Map.of("success", true, "message", "Order status updated", "order", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
