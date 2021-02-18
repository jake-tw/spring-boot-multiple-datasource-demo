package com.jake.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Customer {

    @Id
    private long id;
    private String customerId;
    private String customerName;
}
