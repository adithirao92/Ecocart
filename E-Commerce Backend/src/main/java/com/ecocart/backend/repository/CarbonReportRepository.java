package com.ecocart.backend.repository;
import com.ecocart.backend.entity.CarbonReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CarbonReportRepository extends JpaRepository<CarbonReport, Long> {
    Optional<CarbonReport> findByOrderId(Long orderId);
}