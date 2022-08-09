package com.batonsystems.banksimulator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.batonsystems.banksimulator.constants.Constants;
import com.batonsystems.banksimulator.entity.Customer;
import com.batonsystems.banksimulator.exception.CustomerException;
import com.batonsystems.banksimulator.service.CustomerService;

//This CustomerController class is responsible to create the endpoints and to send and receive request related to Customer entity.

@RestController
public class CustomerController {

	@Autowired 
	private CustomerService customerService;
	
	
	//To Get All The Customers
	@GetMapping("/getCustomers")
	public List<Customer> getCustomers(){
		return customerService.getCustomers();
	}
	
	//To Get Customer Details of a particular Customer Id 
	@GetMapping("/getCustomerByCustomerId/{customerId}")
	public ResponseEntity<?> getCustomerByCustomerId(@PathVariable long customerId) {
		try {
			return ResponseEntity.ok(customerService.getCustomerByCustomerId(customerId));
		}
		catch(CustomerException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());			
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
	
	//To Get Customer Details of a particular Customer Name 
	@GetMapping("/getCustomerByCustomerName/{customerName}")
	public ResponseEntity<?> getCustomerByCustomerName(@PathVariable String customerName) {
		try {
			return ResponseEntity.ok(customerService.getCustomerByCustomerName(customerName));
		}
		catch(CustomerException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());			
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
	
	
	//To Get All The Customers Based On Sorted Order
		@GetMapping("/getSortedCustomers/{order}")
		public List<Customer> getSortedAssets(@PathVariable String order) {
				return customerService.getSortedCustomers(order);
		}
		
		//To Add A Customer
		@PostMapping("/addCustomer")
		public ResponseEntity<?> addCustomer(@RequestBody Customer newCustomer) {
			try {
				customerService.addCustomer(newCustomer);
				return ResponseEntity.ok(Constants.CUSTOMER_ADDED_SUCCESS_RESPONSE);
			}
			catch(CustomerException exception) {
				return ResponseEntity.badRequest().body(exception.getMessage());			
			}
			catch(Exception exception) {
				return ResponseEntity.internalServerError().body("Something Went Wrong");
			}
		}

		//To Update An Existing Customer
		@PutMapping("/updateCustomer")
		public ResponseEntity<?> updateCustomer(@RequestBody Customer updateCustomer) {
			try {
				customerService.updateCustomer(updateCustomer);
				return ResponseEntity.ok(Constants.CUSTOMER_UPDATED_SUCCESS_RESPONSE);
			}
			catch(CustomerException exception) {
				return ResponseEntity.badRequest().body(exception.getMessage());			
			}
			catch(Exception exception) {
				exception.printStackTrace();
				return ResponseEntity.internalServerError().body("Something Went Wrong");
			}
		}
		
		//To Delete Customer Based On Customer Id
		@DeleteMapping("/deleteCustomerByCustomerId/{customerId}")
		public ResponseEntity<?> deleteCustomerByCustomerId(@PathVariable long customerId) {
			try {
				customerService.deleteCustomerByCustomerId(customerId);
				return ResponseEntity.ok(Constants.CUSTOMER_DELETED_SUCCESS_RESPONSE);
			}
			catch(CustomerException exception) {
				return ResponseEntity.badRequest().body(exception.getMessage());			
			}
			catch(Exception exception) {
				return ResponseEntity.internalServerError().body("Something Went Wrong");
			}
		}
		
		
		//To Delete Customer Based On Customer Name
		@DeleteMapping("/deleteCustomerByCustomerName/{customerName}")
		public ResponseEntity<?> deleteCustomerByCustomerName(@PathVariable String customerName) {
			try {
				customerService.deleteCustomerByCustomerName(customerName);
				return ResponseEntity.ok(Constants.CUSTOMER_DELETED_SUCCESS_RESPONSE);
			}
			catch(CustomerException exception) {
				return ResponseEntity.badRequest().body(exception.getMessage());			
			}
			catch(Exception exception) {
				return ResponseEntity.internalServerError().body("Something Went Wrong");
			}
		}
		
		
}
