package com.batonsystems.banksimulator.service;

import java.util.List;

import com.batonsystems.banksimulator.entity.Customer;
import com.batonsystems.banksimulator.exception.CustomerException;

public interface CustomerService {

	public List<Customer> getCustomers();

	public Customer getCustomerByCustomerId(long customerId) throws CustomerException;

	public Customer getCustomerByCustomerName(String customerName) throws CustomerException;

	public Customer addCustomer(Customer newCustomer) throws CustomerException;

	public List<Customer> getSortedCustomers(String order);

	public Customer updateCustomer(Customer updateCustomer) throws CustomerException;

	public void deleteCustomerByCustomerId(long customerId) throws CustomerException;

	public void deleteCustomerByCustomerName(String customerName) throws CustomerException;

}