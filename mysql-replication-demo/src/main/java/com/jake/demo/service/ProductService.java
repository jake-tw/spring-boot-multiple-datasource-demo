package com.jake.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jake.demo.entity.Product;
import com.jake.demo.repository.ProductRepository;
import com.jake.demo.util.CommonUtil;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    public Map<String, List<Product>> getAllProduct() {
        Map<String, List<Product>> r = new HashMap<>();
        r.put("source", productService.findAllBySource());
        r.put("replicas", findAllByRepicas());
        return r;
    }

    @Transactional(readOnly = false)
    public List<Product> findAllBySource() {
        return productRepository.findAll();
    }

    public List<Product> findAllByRepicas() {
        return productRepository.findAll();
    }

    public Product insertProduct(String productName) {
        Product product = new Product();
        product.setProductId(CommonUtil.getUUID());
        product.setProductName(productName);
        return productRepository.save(product);
    }
}
