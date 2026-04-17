package com.ecocart.backend.service;
import com.ecocart.backend.entity.PromoCode;
import com.ecocart.backend.repository.PromoCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class DiscountEngine {
    @Autowired
    private PromoCodeRepository promoRepository;

    public double applyDiscount(String code, double orderTotal) {
        PromoCode promo = promoRepository.findByCode(code)
            .orElseThrow(() -> new RuntimeException("Invalid promo code"));

        if (!promo.isActive() || promo.getExpiryDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Promo code expired");
        }
        if (orderTotal < promo.getMinOrderValue()) {
            throw new RuntimeException("Order total too low for this promo");
        }

        return orderTotal - (orderTotal * promo.getDiscountValue() / 100);
    }
}