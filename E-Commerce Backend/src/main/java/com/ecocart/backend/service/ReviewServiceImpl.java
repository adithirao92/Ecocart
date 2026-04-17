// package com.ecocart.backend.service;

// import com.ecocart.backend.entity.Review;
// import com.ecocart.backend.repository.ReviewRepository;
// import org.springframework.stereotype.Service;
// import java.util.List;
// import java.time.LocalDate;

// @Service
// public class ReviewServiceImpl implements ReviewService {

//     private final ReviewRepository repo;

//     public ReviewServiceImpl(ReviewRepository repo) {
//         this.repo = repo;
//     }

//     public Review addReview(Review review) {

//         return repo.save(review);
//     }

//     public List<Review> getByProduct(Long productId) {
//         return repo.findByProductId(productId);
//     }

//     public double getAverage(Long productId) {
//         List<Review> reviews = repo.findByProductId(productId);
//         return reviews.stream().mapToInt(Review::getScore).average().orElse(0.0);
//     }
// }

package com.ecocart.backend.service;

import com.ecocart.backend.entity.Review;
import com.ecocart.backend.entity.Product;
import com.ecocart.backend.repository.ReviewRepository;
import com.ecocart.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDate;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repo;
    private final ProductRepository productRepo; // 1. Added Product Repository!

    // 2. Updated Constructor Injection
    public ReviewServiceImpl(ReviewRepository repo, ProductRepository productRepo) {
        this.repo = repo;
        this.productRepo = productRepo;
    }

    @Override
    public Review addReview(Review review) {
        // STEP 1: Save the new review to the database
        Review savedReview = repo.save(review);

        // STEP 2: The "Observer" Action - Fetch all reviews and calculate new average
        List<Review> reviews = repo.findByProductId(review.getProductId());
        
        // (Using Abhigna's existing stream logic to get the math right!)
        double newAverage = reviews.stream().mapToInt(Review::getScore).average().orElse(0.0);
        double roundedAverage = Math.round(newAverage * 10.0) / 10.0; // Round to 1 decimal place

        // STEP 3: Find the Product and update its state
        Product product = productRepo.findById(review.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + review.getProductId()));
        
        product.setAverageRating(roundedAverage);
        product.setTotalReviews(reviews.size());

        // STEP 4: Save the updated product back to the database
        productRepo.save(product);

        return savedReview;
    }

    @Override
    public List<Review> getByProduct(Long productId) {
        return repo.findByProductId(productId);
    }

    @Override
    public double getAverage(Long productId) {
        List<Review> reviews = repo.findByProductId(productId);
        return reviews.stream().mapToInt(Review::getScore).average().orElse(0.0);
    }
}