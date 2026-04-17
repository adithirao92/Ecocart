package com.ecocart.backend.service;
import java.util.Map;
import com.ecocart.backend.entity.Order;

public interface OrderService {
    Order createOrder(Order order);
    Order getOrderDetails(Long orderId);
    Order updateOrderStatus(Long orderId, Order.Status status);
    Map<String, Object> checkout(Long userId, Map<String, Object> payload);
}
