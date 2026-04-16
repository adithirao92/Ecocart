package com.ecocart.backend.service;

import com.ecocart.backend.entity.Review;
import java.util.List;

public interface ReviewService {
    Review addReview(Review review);
    List<Review> getByProduct(Long productId);
    double getAverage(Long productId);
}