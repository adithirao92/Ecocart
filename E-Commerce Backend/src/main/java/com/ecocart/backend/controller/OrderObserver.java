package com.ecocart.backend.observer;

public interface OrderObserver {
    void update(Long orderId, String status);
}