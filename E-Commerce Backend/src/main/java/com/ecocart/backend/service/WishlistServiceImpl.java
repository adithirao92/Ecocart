package com.ecocart.backend.service;

import com.ecocart.backend.entity.Product;
import com.ecocart.backend.entity.Wishlist;
import com.ecocart.backend.repository.ProductRepository;
import com.ecocart.backend.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void add(Long userId, Long productId) {

        // prevent duplicates
        if (!wishlistRepository.existsByUserIdAndProductId(userId, productId)) {
            Wishlist w = new Wishlist();
            w.setUserId(userId);
            w.setProductId(productId);

            wishlistRepository.save(w);
        }
    }

    @Override
    public List<Product> getByUser(Long userId) {

        List<Wishlist> wishlist = wishlistRepository.findByUserId(userId);

        return wishlist.stream()
                .map(w -> productRepository.findById(w.getProductId()).orElse(null))
                .collect(Collectors.toList());
    }

    @Override
    public void remove(Long userId, Long productId) {
        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
    }
}