package com.jake.demo.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jake.demo.entity.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
