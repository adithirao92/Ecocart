package com.ecocart.backend.service;

import com.ecocart.backend.entity.Product;
import com.ecocart.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        // For creating a NEW product, Spring Boot automatically grabs the URL from the JSON
        // because we added the getter/setter to Product.java earlier!
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Product existing = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setCategoryId(product.getCategoryId());
        existing.setStockQuantity(product.getStockQuantity());
        
        // 🔥 HERE IS THE MAGIC LINE THAT WAS MISSING!
        existing.setImageUrl(product.getImageUrl()); 

        return productRepository.save(existing);
    }

    @Override
    public Product getProductDetails(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}