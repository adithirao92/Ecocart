package com.ecocart.backend.service;

import com.ecocart.backend.entity.Review;
import com.ecocart.backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDate;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repo;

    public ReviewServiceImpl(ReviewRepository repo) {
        this.repo = repo;
    }

    public Review addReview(Review review) {

        return repo.save(review);
    }

    public List<Review> getByProduct(Long productId) {
        return repo.findByProductId(productId);
    }

    public double getAverage(Long productId) {
        List<Review> reviews = repo.findByProductId(productId);
        return reviews.stream().mapToInt(Review::getScore).average().orElse(0.0);
    }
}