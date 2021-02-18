package com.jake.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Product {

    @Id
    private long id;
    private String productId;
    private String productName;
}
