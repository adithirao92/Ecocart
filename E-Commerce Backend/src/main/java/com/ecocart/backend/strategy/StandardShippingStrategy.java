package com.ecocart.backend.strategy;

public class StandardShippingStrategy implements ShippingCarbonStrategy {
    @Override
    public double calculateCO2(double weightKg, double distanceKm) {
        return weightKg * distanceKm * 0.21; // kg CO2 per kg per km
    }
}