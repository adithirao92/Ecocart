package com.echocart.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
            "message", "EchoCart Backend API is running!",
            "status", "success",
            "endpoints", Map.of(
                "products", "/api/products",
                "users", "/api/users",
                "cart", "/api/cart",
                "orders", "/api/orders"
            )
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}