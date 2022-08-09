package com.batonsystems.banksimulator.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.batonsystems.banksimulator.constants.Constants;
import com.batonsystems.banksimulator.entity.Customer;
import com.batonsystems.banksimulator.exception.CustomerException;
import com.batonsystems.banksimulator.repository.CustomerRepository;
import com.batonsystems.banksimulator.service.CustomerService;

//This CustomerServiceImpl class holds the business logic for operations related to Customer entity.
//Performed CRUD Operation for Customer entity.
@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	Logger logger=LoggerFactory.getLogger(this.getClass().getSimpleName()); 

	@Override
	public List<Customer> getCustomers() {
		// TODO Auto-generated method stub
		logger.info("Fetched All Customers");
		return customerRepo.findAll();
	}

	@Override
	public Customer getCustomerByCustomerId(long customerId) throws CustomerException {
		// TODO Auto-generated method stub
		Customer customerDetails=customerRepo.getByCustomerId(customerId);
		if(customerDetails==null) throw new CustomerException("Customer Id is Invalid!!");
		logger.info("Fetched Customer. customer id:{}",customerDetails.getCustomerId());
		return customerDetails;
	}

	@Override
	public Customer getCustomerByCustomerName(String customerName) throws CustomerException {
		// TODO Auto-generated method stub
	   	if (customerName == null || customerName.trim().isEmpty()) {
    		throw new CustomerException("The Customer Name Can't be null or empty");
    	}
		Customer customerDetails= customerRepo.getByCustomerName(customerName);
		if(customerDetails==null) throw new CustomerException("Customer Name Is Invalid");
		logger.info("Fetched Customer. customer id:{}",customerDetails.getCustomerId());
		return customerDetails;
	}
	
	@Override
	public List<Customer> getSortedCustomers(String order) {
		// TODO Auto-generated method stub
		if(order.equals(Constants.DESC)) {
			List<Customer> customerDescList=customerRepo.findAll(Sort.by(Sort.Direction.DESC,Constants.CUSTOMER_NAME));
			logger.info("Return all Customers in Descending Order");
			return customerDescList;
		}
		else {
			List<Customer> customerAscList=customerRepo.findAll(Sort.by(Sort.Direction.ASC,Constants.CUSTOMER_NAME));
			logger.info("Return all Customers in Ascending Order");
			return customerAscList;
		}
	}

	@Override
	public Customer addCustomer(Customer newCustomer) throws CustomerException{
		// TODO Auto-generated method stub
		if(newCustomer==null) throw new CustomerException("Customer can't be empty!!");
		if(newCustomer.getCustomerName()==null || newCustomer.getCustomerName().trim().isEmpty()) throw new CustomerException("Customer Name can't be Empty");
		logger.info("Customer added. customer id:{}",newCustomer.getCustomerId());
		return customerRepo.save(newCustomer);
	}

	@Override
	public Customer updateCustomer(Customer updateCustomer) throws CustomerException {
		// TODO Auto-generated method stub
		if(updateCustomer==null) throw new CustomerException("Customer can't be empty!!");
		if(updateCustomer.getCustomerName()==null || updateCustomer.getCustomerName().trim().isEmpty()) throw new CustomerException("Customer Name can't be Empty");
		logger.info("Customer Updated. customer id:{}",updateCustomer.getCustomerId());
		return customerRepo.save(updateCustomer);
	}

	@Override
	public void deleteCustomerByCustomerId(long customerId) throws CustomerException {
		// TODO Auto-generated method stub
		Customer customerDetails=customerRepo.getByCustomerId(customerId);
		if(customerDetails==null) throw new CustomerException("Customer Id is not present");
		customerRepo.delete(customerDetails);
		logger.info("Customer Deleted. customer id:{}",customerDetails.getCustomerId());
	}

	@Override
	public void deleteCustomerByCustomerName(String customerName) throws CustomerException {
		// TODO Auto-generated method stub
		if(customerName==null || customerName.trim().isEmpty()) throw new CustomerException("Customer Name can't be empty!!");
		Customer customerDetails=customerRepo.getByCustomerName(customerName);
		if(customerDetails==null) throw new CustomerException("Customer Name is not present");
		customerRepo.delete(customerDetails);
		logger.info("Customer Deleted. customer id:{}",customerDetails.getCustomerId());
	}

}