package com.ecocart.backend.controller;
import com.ecocart.backend.service.CarbonFootprintCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/carbon")
public class CarbonController {

    @Autowired
    private CarbonFootprintCalculator calculator;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateCarbon(@RequestBody Map<String, Object> payload) {
        Long orderId = Long.valueOf(payload.get("orderId").toString());
        String shippingType = payload.get("shippingType").toString();
        double weightKg = Double.parseDouble(payload.get("weightKg").toString());
        double distanceKm = Double.parseDouble(payload.get("distanceKm").toString());

        return ResponseEntity.ok(calculator.calculate(orderId, shippingType, weightKg, distanceKm));
    }
}