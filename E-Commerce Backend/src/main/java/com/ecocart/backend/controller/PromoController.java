package com.ecocart.backend.controller;
import com.ecocart.backend.entity.PromoCode;
import com.ecocart.backend.service.DiscountEngine;
import com.ecocart.backend.repository.PromoCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/promo")
public class PromoController {

    @Autowired
    private DiscountEngine discountEngine;
    
    @Autowired
    private PromoCodeRepository promoRepo;

    @PostMapping("/admin/create")
    public ResponseEntity<?> createPromo(@RequestBody PromoCode promo) {
        return ResponseEntity.ok(promoRepo.save(promo));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateCode(@RequestBody Map<String, Object> payload) {
        String code = payload.get("code").toString();
        double total = Double.parseDouble(payload.get("orderTotal").toString());
        
        try {
            double newTotal = discountEngine.applyDiscount(code, total);
            return ResponseEntity.ok(Map.of("success", true, "newTotal", newTotal));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}