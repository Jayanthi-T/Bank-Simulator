package com.batonsystems.banksimulator.unittest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.batonsystems.banksimulator.constants.Constants;
import com.batonsystems.banksimulator.entity.Customer;
import com.batonsystems.banksimulator.exception.AssetException;
import com.batonsystems.banksimulator.exception.CustomerException;
import com.batonsystems.banksimulator.repository.CustomerRepository;
import com.batonsystems.banksimulator.service.impl.CustomerServiceImpl;

//This CustomerServiceTest class is responsible to test the functionality related to the customer entity.
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CustomerServiceTest {
	
	@Autowired
	public CustomerRepository customerRepo;
	
	@Autowired
	CustomerServiceImpl customerServiceImpl;
	
	@Test
	@DirtiesContext
	public void testGetCustomers() {
		List<Customer> customerList=new ArrayList<Customer>();
		customerList.add(new Customer(1,"Priyam"));
		customerList.add(new Customer(2,"Pranjal"));
		customerRepo.saveAll(customerList);
		assertEquals(2,customerServiceImpl.getCustomers().size());
	}
	
	@Test
	@DirtiesContext
	public void testGetCustomerByCustomerId() throws CustomerException {
		customerRepo.save(new Customer(1,"Priyam"));
		assertEquals("Priyam",customerServiceImpl.getCustomerByCustomerId(1).getCustomerName());
	}
	
	@Test
	@DirtiesContext
	public void testGetCustomerByCustomerIdForExceptionHandling() throws CustomerException {
		assertThrows(CustomerException.class,()->customerServiceImpl.getCustomerByCustomerId(1));
	}

	@Test
	@DirtiesContext
	public void testGetCustomerByCustomerName() throws CustomerException {
		customerRepo.save(new Customer(1,"Priyam"));
		assertEquals(1,customerServiceImpl.getCustomerByCustomerName("Priyam").getCustomerId());
	}
	
	@Test
	@DirtiesContext
	public void testGetCustomerByCustomerNameForExceptionHandling() throws CustomerException {
		assertThrows(CustomerException.class,()->customerServiceImpl.getCustomerByCustomerName(" "));
	}
	
	@Test
	@DirtiesContext
	public void testGetSortedAscCustomers() throws AssetException {
		List<Customer> customerList= new ArrayList<Customer>();
		customerList.add(new Customer(1,"Priyam"));
		customerList.add(new Customer(2,"Pranjal"));
		customerRepo.saveAll(customerList);
		List<Customer> expectedAssetList=new ArrayList<Customer>(List.of(new Customer(2,"Pranjal"), new Customer(1,"Priyam")));
		List<Customer> actualAssetList=customerServiceImpl.getSortedCustomers(Constants.ASC);
		assertEquals(expectedAssetList.size(),actualAssetList.size());
		for(int i=0;i<expectedAssetList.size();i++) {
			boolean isCustomerNameMatching= expectedAssetList.get(i).getCustomerName() == actualAssetList.get(i).getCustomerName()?true:false;
			boolean isCustomerIdMatching=expectedAssetList.get(i).getCustomerId() == actualAssetList.get(i).getCustomerId()?true:false;
			assertAll(
			()->assertEquals(true,isCustomerNameMatching),
			()->assertEquals(true,isCustomerIdMatching)
			);
		}
	}
	
	@Test
	@DirtiesContext
	public void testGetSortedDescCustomers() throws AssetException {
		List<Customer> customerList= new ArrayList<Customer>();
		customerList.add(new Customer(1,"Pranjal"));
		customerList.add(new Customer(2,"Priyam"));
		customerRepo.saveAll(customerList);
		List<Customer> expectedAssetList=new ArrayList<Customer>(List.of(new Customer(2,"Priyam"), new Customer(1,"Pranjal")));
		List<Customer> actualAssetList=customerServiceImpl.getSortedCustomers(Constants.DESC);
		assertEquals(expectedAssetList.size(),actualAssetList.size());
		for(int i=0;i<expectedAssetList.size();i++) {
			boolean isCustomerNameMatching= expectedAssetList.get(i).getCustomerName() == actualAssetList.get(i).getCustomerName()?true:false;
			boolean isCustomerIdMatching=expectedAssetList.get(i).getCustomerId() == actualAssetList.get(i).getCustomerId()?true:false;
			assertAll(
			()->assertEquals(true,isCustomerNameMatching),
			()->assertEquals(true,isCustomerIdMatching)
			);
		}
	}
	
	@Test
	@DirtiesContext
	public void testAddCustomer() throws CustomerException {
		Customer customerDetails= new Customer(1,"Priyam");
		customerServiceImpl.addCustomer(customerDetails);
		boolean isCustomerIdPresent=customerRepo.existsByCustomerId(1);
		boolean isCustomerNamePresent=customerRepo.existsByCustomerName("Priyam");
		assertAll(
				()->assertNotNull(customerDetails),
				()->assertEquals(true,isCustomerIdPresent),
				()->assertEquals(true,isCustomerNamePresent)
				);
	}
	
	@Test
	@DirtiesContext
	public void testAddCustomerForExceptionalHandling() throws CustomerException {
		Customer customerDetails= new Customer();
		assertThrows(CustomerException.class,()->customerServiceImpl.addCustomer(customerDetails));
	}
	
	@Test
	@DirtiesContext
	public void testUpdateCustomer() throws CustomerException{
		Customer customerDetails= new Customer(1,"Priyam");
		customerServiceImpl.updateCustomer(customerDetails);
		boolean isCustomerIdPresent=customerRepo.existsByCustomerId(1);
		boolean isCustomerNamePresent=customerRepo.existsByCustomerName("Priyam");
		assertAll(
				()->assertNotNull(customerDetails),
				()->assertEquals(true,isCustomerIdPresent),
				()->assertEquals(true,isCustomerNamePresent)
				);
	}
	
	@Test
	@DirtiesContext
	public void testUpdateCustomerForExceptionalHandling() throws CustomerException {
		Customer customerDetails= new Customer();
		assertThrows(CustomerException.class,()->customerServiceImpl.updateCustomer(customerDetails));
	}
	
	
	@Test
	@DirtiesContext
	public void deleteCustomerByCustomerId() throws CustomerException{
		Customer customerDetails=new Customer(1,"Priyam");
		customerRepo.save(customerDetails);
		customerServiceImpl.deleteCustomerByCustomerId(1);
		boolean isCustomerPresent=customerRepo.existsByCustomerId(1);
		assertEquals(false,isCustomerPresent);
	}
	
	@Test
	@DirtiesContext
	public void deleteCustomerByCustomerIdForExceptionalHandling() throws CustomerException{
		assertThrows(CustomerException.class,()->customerServiceImpl.deleteCustomerByCustomerId(1));
	}
	
	@Test
	@DirtiesContext
	public void deleteCustomerByCustomerName() throws CustomerException{
		Customer customerDetails=new Customer(1,"Priyam");
		customerRepo.save(customerDetails);
		customerServiceImpl.deleteCustomerByCustomerName("Priyam");
		boolean isCustomerPresent=customerRepo.existsByCustomerName("Priyam");
		assertEquals(false,isCustomerPresent);
	}
	
	@Test
	@DirtiesContext
	public void deleteCustomerByCustomerNameForExceptionalHandling() throws CustomerException{
		assertThrows(CustomerException.class,()->customerServiceImpl.deleteCustomerByCustomerName(" "));
	}
	
	
}
