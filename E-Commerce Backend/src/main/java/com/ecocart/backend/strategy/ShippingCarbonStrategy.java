package com.ecocart.backend.strategy;

public interface ShippingCarbonStrategy {
    double calculateCO2(double weightKg, double distanceKm);
}

