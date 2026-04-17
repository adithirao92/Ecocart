package com.ecocart.backend.service;

import com.ecocart.backend.entity.Product;
import java.util.List;

public interface WishlistService {

    void add(Long userId, Long productId);

    List<Product> getByUser(Long userId);

    void remove(Long userId, Long productId);
}