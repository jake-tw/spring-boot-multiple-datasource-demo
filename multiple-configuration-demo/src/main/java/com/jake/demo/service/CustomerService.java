package com.jake.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jake.demo.entity.customer.Customer;
import com.jake.demo.repository.customer.CustomerRepository;
import com.jake.demo.util.CommonUtil;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer insertCustomer(String customerName) {
        Customer customer = new Customer();
        customer.setCustomerId(CommonUtil.getUUID());
        customer.setCustomerName(customerName);
        return customerRepository.save(customer);
    }
}
