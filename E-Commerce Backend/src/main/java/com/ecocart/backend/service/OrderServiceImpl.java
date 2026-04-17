package com.ecocart.backend.service;

import com.ecocart.backend.entity.Order;
import com.ecocart.backend.repository.OrderRepository;
import com.ecocart.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ecocart.backend.entity.Cart;
import com.ecocart.backend.repository.CartRepository;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CarbonFootprintCalculator carbonCalculator;

    public OrderServiceImpl(OrderRepository orderRepository,
                        UserRepository userRepository,
                        CartRepository cartRepository,
                        CarbonFootprintCalculator carbonCalculator) {
    this.orderRepository = orderRepository;
    this.userRepository = userRepository;
    this.cartRepository = cartRepository;
    this.carbonCalculator = carbonCalculator;
}

    @Override
    public Order createOrder(Order order) {
        validateOrder(order);

        // Verify user exists
        if (!userRepository.existsById(order.getUserId())) {
            throw new RuntimeException("User not found with ID: " + order.getUserId());
        }

        order.setOrderDate(LocalDate.now());
        order.setStatus(Order.Status.PENDING);

        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderDetails(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    @Override
    public Order updateOrderStatus(Long orderId, Order.Status status) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        Order existing = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        existing.setStatus(status);
        return orderRepository.save(existing);
    }
    
@Override
public Map<String, Object> checkout(Long userId, Map<String, Object> payload) {

    // STEP 1: Validate user
    if (!userRepository.existsById(userId)) {
        throw new RuntimeException("User not found with ID: " + userId);
    }

    List<Cart> carts = cartRepository.findByUserId(userId);

    if (carts.isEmpty()) {
        throw new RuntimeException("Cart not found");
    }

    double totalAmount = 1000;

    // STEP 2: Create order
    Order order = new Order();
    order.setUserId(userId);
    order.setOrderDate(LocalDate.now());
    order.setStatus(Order.Status.PENDING);
    order.setTotalAmount(java.math.BigDecimal.valueOf(totalAmount));

    // STEP 3: Payment simulation
    boolean paymentSuccess = processPayment(totalAmount);

    if (!paymentSuccess) {
        throw new RuntimeException("Payment failed");
    }

    order.setStatus(Order.Status.PAID);

    Order savedOrder = orderRepository.save(order);

    // STEP 4: Carbon calculation
    // double co2 = carbonCalculator.calculate("STANDARD", 2.0, 10.0);
    var report = carbonCalculator.calculate(savedOrder.getOrderId(), "STANDARD", 2.0, 10.0);
    double co2 = report.getEstimatedCO2();

    return Map.of(
            "success", true,
            "message", "Checkout + Payment successful",
            "orderId", savedOrder.getOrderId(),
            "total", totalAmount,
            "carbonEmission", co2,
            "status", savedOrder.getStatus()
    );

    
}
private boolean processPayment(double amount) {
    System.out.println("Processing payment of ₹" + amount);
    return true;
}
    private void validateOrder(Order order) {
        if (order.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (order.getTotalAmount() == null || order.getTotalAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total amount must be greater than 0");
        }
    }
}