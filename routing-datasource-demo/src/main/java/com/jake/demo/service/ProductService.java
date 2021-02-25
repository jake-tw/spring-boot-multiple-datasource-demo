package com.jake.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jake.demo.entity.Product;
import com.jake.demo.repository.ProductRepository;
import com.jake.demo.util.CommonUtil;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product insertProduct(String productName) {
        Product product = new Product();
        product.setProductId(CommonUtil.getUUID());
        product.setProductName(productName);
        return productRepository.save(product);
    }
}
