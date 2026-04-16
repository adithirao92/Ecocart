package com.ecocart.backend.strategy;

public class EcoShippingStrategy implements ShippingCarbonStrategy {
    @Override
    public double calculateCO2(double weightKg, double distanceKm) {
        return weightKg * distanceKm * 0.07;
    }
}