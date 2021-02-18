package com.jake.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jake.demo.entity.Customer;
import com.jake.demo.repository.CustomerMainRepository;
import com.jake.demo.repository.CustomerSubRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerMainRepository customerMainRepository;

    @Autowired
    private CustomerSubRepository customerSubRepository;

    public Customer getMainCustomer(String customerId) {
        return customerMainRepository.findByCustomerId(customerId);
    }

    public Customer getSubCustomer(String customerId) {
        return customerSubRepository.findByCustomerId(customerId);
    }
}
