package com.jake.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.jake.demo.entity.Product;
import com.jake.demo.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProduct(@RequestHeader(value = "group", required = false) String group) {
        log.info("request header: {}", group);
        return productService.getAllProduct();
    }
}
