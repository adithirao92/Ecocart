package com.ecocart.backend.strategy;

public class ExpressShippingStrategy implements ShippingCarbonStrategy {
    @Override
    public double calculateCO2(double weightKg, double distanceKm) {
        return weightKg * distanceKm * 0.35; // higher emissions
    }
}