package com.ecocart.backend.service;
import com.ecocart.backend.entity.CarbonReport;
import com.ecocart.backend.strategy.*;
import com.ecocart.backend.repository.CarbonReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class CarbonFootprintCalculator {
    @Autowired
    private CarbonReportRepository reportRepository;

    public CarbonReport calculate(Long orderId, String shippingType, double weightKg, double distanceKm) {
        ShippingCarbonStrategy strategy = switch (shippingType.toUpperCase()) {
            case "EXPRESS" -> new ExpressShippingStrategy();
            case "ECO" -> new EcoShippingStrategy();
            default -> new StandardShippingStrategy();
        };

        double co2 = strategy.calculateCO2(weightKg, distanceKm);
        String rating = co2 < 1.0 ? "LOW" : co2 < 5.0 ? "MEDIUM" : "HIGH";

        CarbonReport report = new CarbonReport();
        report.setOrderId(orderId);
        report.setShippingType(shippingType);
        report.setEstimatedCO2(co2);
        report.setRating(rating);
        report.setGeneratedAt(LocalDateTime.now());

        return reportRepository.save(report); // Save to DB
    }
}