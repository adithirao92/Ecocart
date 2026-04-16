package com.ecocart.backend.service;

import com.ecocart.backend.strategy.*;
import org.springframework.stereotype.Service;

@Service
public class CarbonFootprintCalculator {

    public double calculate(String shippingType, double weight, double distance) {

        ShippingCarbonStrategy strategy;

        switch (shippingType.toUpperCase()) {
            case "EXPRESS":
                strategy = new ExpressShippingStrategy();
                break;
            case "ECO":
                strategy = new EcoShippingStrategy();
                break;
            default:
                strategy = new StandardShippingStrategy();
        }

        return strategy.calculateCO2(weight, distance);
    }
}