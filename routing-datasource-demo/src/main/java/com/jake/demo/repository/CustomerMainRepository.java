package com.jake.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jake.demo.annotation.DataSourceRouter;
import com.jake.demo.entity.Customer;
import com.jake.demo.model.DataSourceType;

@Repository
@DataSourceRouter(DataSourceType.MAIN)
public interface CustomerMainRepository extends JpaRepository<Customer, Long> {

    Customer findByCustomerId(String customerId);
}
