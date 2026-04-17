package com.ecocart.backend.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "carbon_reports")
public class CarbonReport {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    private Long orderId;
    private String shippingType; 
    private Double estimatedCO2; 
    private String rating; 
    private LocalDateTime generatedAt;

    // Getters and Setters
    public Long getReportId() { return reportId; }
    public void setReportId(Long reportId) { this.reportId = reportId; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getShippingType() { return shippingType; }
    public void setShippingType(String shippingType) { this.shippingType = shippingType; }
    public Double getEstimatedCO2() { return estimatedCO2; }
    public void setEstimatedCO2(Double estimatedCO2) { this.estimatedCO2 = estimatedCO2; }
    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
}