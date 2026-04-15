package com.ecocart.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    private Long customerId;
    private String subject;
    private String description;

    @Enumerated(EnumType.STRING)
    private TicketStatus status = TicketStatus.OPEN;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum TicketStatus {
        OPEN, IN_PROGRESS, CLOSED
    }

    // getters & setters
    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}