package com.jake.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jake.demo.entity.Customer;
import com.jake.demo.service.CustomerService;

@RestController("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/main/{customerId}")
    public Customer getMainCustomer(@PathVariable("customerId") String customerId) {
        return customerService.getMainCustomer(customerId);
    }

    @GetMapping("/sub/{customerId}")
    public Customer getSubCustomer(@PathVariable("customerId") String customerId) {
        return customerService.getSubCustomer(customerId);
    }
}
