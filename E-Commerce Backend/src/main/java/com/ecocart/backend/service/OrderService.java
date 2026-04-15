package com.ecocart.backend.service;

import com.ecocart.backend.entity.Order;

public interface OrderService {
    Order createOrder(Order order);
    Order getOrderDetails(Long orderId);
    Order updateOrderStatus(Long orderId, Order.Status status);
}
