package com.jake.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jake.demo.entity.customer.Customer;
import com.jake.demo.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public List<Customer> getAllCustomer(@RequestHeader(value = "group", required = false) String group) {
        return customerService.getAllCustomer();
    }

    @PostMapping("/{customertName}")
    public Customer insertCustomer(@RequestHeader(value = "group", required = false) String group,
            @PathVariable String customertName) {
        return customerService.insertCustomer(customertName);
    }
}
