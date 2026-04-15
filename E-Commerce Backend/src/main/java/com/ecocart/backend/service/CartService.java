package com.ecocart.backend.service;

import com.ecocart.backend.entity.Cart;
import java.util.List;

public interface CartService {
    Cart addToCart(Cart cart);
    void removeFromCart(Long cartId);
    List<Cart> getCartDetails(Long userId);
}
