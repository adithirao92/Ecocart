package com.ecocart.backend.controller;

import com.ecocart.backend.entity.Review;
import com.ecocart.backend.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @PostMapping
    public Review addReview(@RequestBody Review review) {
        return service.addReview(review);
    }

    @GetMapping("/product/{productId}")
    public List<Review> getReviews(@PathVariable Long productId) {
        return service.getByProduct(productId);
    }

    @GetMapping("/product/{productId}/average")
    public double getAverage(@PathVariable Long productId) {
        return service.getAverage(productId);
    }
}