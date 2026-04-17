package com.ecocart.backend.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "promo_codes")
public class PromoCode {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promoId;
    private String code;
    private Double discountValue; 
    private LocalDate expiryDate;
    private Double minOrderValue;
    private boolean active = true;

    // Getters and Setters
    public Long getPromoId() { return promoId; }
    public void setPromoId(Long promoId) { this.promoId = promoId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Double getDiscountValue() { return discountValue; }
    public void setDiscountValue(Double discountValue) { this.discountValue = discountValue; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public Double getMinOrderValue() { return minOrderValue; }
    public void setMinOrderValue(Double minOrderValue) { this.minOrderValue = minOrderValue; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}