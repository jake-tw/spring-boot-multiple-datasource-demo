package com.jake.demo.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jake.demo.entity.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
