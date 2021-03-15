package com.jake.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jake.demo.entity.product.Product;
import com.jake.demo.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public List<Product> getAllProduct(@RequestHeader(value = "group", required = false) String group) {
        return productService.getAllProduct();
    }

    @PostMapping("/{productName}")
    public Product insertProduct(@RequestHeader(value = "group", required = false) String group,
            @PathVariable String productName) {
        return productService.insertProduct(productName);
    }
}
