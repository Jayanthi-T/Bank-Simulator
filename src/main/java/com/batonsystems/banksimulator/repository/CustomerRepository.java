package com.batonsystems.banksimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.batonsystems.banksimulator.entity.Customer;

//This CustomerRepository class holds the database operations related to Customer entity.
public interface CustomerRepository extends JpaRepository<Customer,Integer>{
	public Customer getByCustomerId(long customerId);
	public Customer getByCustomerName(String customerName);
	public boolean existsByCustomerName(String customerName);
	public boolean existsByCustomerId(long customerId);
}
