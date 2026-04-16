package com.ecocart.backend.controller;

import com.ecocart.backend.entity.SupportTicket;
import com.ecocart.backend.repository.SupportTicketRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class SupportTicketController {

    private final SupportTicketRepository repo;

    public SupportTicketController(SupportTicketRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public SupportTicket create(@RequestBody SupportTicket ticket) {
        return repo.save(ticket);
    }

    @GetMapping("/customer/{id}")
    public List<SupportTicket> getTickets(@PathVariable Long id) {
        return repo.findByCustomerId(id);
    }
}