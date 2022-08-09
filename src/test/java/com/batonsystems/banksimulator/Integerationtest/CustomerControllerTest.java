package com.batonsystems.banksimulator.Integerationtest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.batonsystems.banksimulator.constants.Constants;
import com.batonsystems.banksimulator.entity.Customer;
import com.batonsystems.banksimulator.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

//This CustomerControllerTest is responsible to test the all the functionality related to customer controller.
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class CustomerControllerTest {

	    @Autowired
	    private MockMvc mockMvc;

	    private ObjectMapper objectMapper = new ObjectMapper();
	    
	    @Autowired
	    private CustomerRepository customerRepo;
	    
	    @Test
	    @DirtiesContext
	    public void testGetCustomers() throws Exception {
	    	List<Customer> customerList= new ArrayList<Customer>();
	    	customerList.add(new Customer(1,"Sham"));
	    	customerList.add(new Customer(2,"Kishan"));
	    	customerRepo.saveAll(customerList);
	    	mockMvc.perform(MockMvcRequestBuilders.get("/getCustomers")
	    	.contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isOk())
	        .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(2)));
	    }
	    
	    @Test
	    @DirtiesContext
	    public void testGetCustomerByCustomerId() throws Exception {
	    	Customer customer=new Customer(1,"Sham");
	    	customerRepo.save(customer);
	    	mockMvc.perform(MockMvcRequestBuilders.get("/getCustomerByCustomerId/1")
	    	    	.contentType(MediaType.APPLICATION_JSON))
	    	    	.andExpect(status().isOk())
	                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("Sham"));
	    }
	    
	    @Test
	    @DirtiesContext
	    public void testGetCustomerByCustomerName() throws Exception {
	    	Customer customer=new Customer(1,"Sham");
	    	customerRepo.save(customer);	    	
	    	mockMvc.perform(MockMvcRequestBuilders.get("/getCustomerByCustomerName/Sham")
	    	    	.contentType(MediaType.APPLICATION_JSON))
	    	    	.andExpect(status().isOk())
	                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(1));
	    }
	    
	    @Test
	    @DirtiesContext
	    public void testAddCustomer() throws Exception {
	    	Customer customer=new Customer(1,"Sham");
	    	customerRepo.save(customer);
	        String customerDetail = objectMapper.writeValueAsString(customer);
	        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/addCustomer")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .content(customerDetail);
	        
	        mockMvc.perform(mockRequest)
	        .andExpect(status().isOk())
	        .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$").value(Constants.CUSTOMER_ADDED_SUCCESS_RESPONSE));
	    	
	    }
	    
	    @Test
	    @DirtiesContext
	    public void testUpdateCustomer() throws Exception {
	    	Customer customer=new Customer(1,"Sham");
	    	customerRepo.save(customer);
	        String customerDetail = objectMapper.writeValueAsString(customer);
	        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/updateCustomer")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .content(customerDetail);
	        
	        mockMvc.perform(mockRequest)
	        .andExpect(status().isOk())
	        .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$").value(Constants.CUSTOMER_UPDATED_SUCCESS_RESPONSE));
	    }
	    
	    
	    @Test
	    @DirtiesContext
	    public void deleteCustomerByCustomerId() throws Exception {
	    	Customer customer=new Customer(1,"Sham");
	    	customerRepo.save(customer);	
	        mockMvc.perform(MockMvcRequestBuilders
	                .delete("/deleteCustomerByCustomerId/1")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());
	    }
	    
	    @Test
	    @DirtiesContext
	    public void deleteCustomerByCustomerName() throws Exception {
		    Customer customerDetail=new Customer(3,"Ram");
		    customerRepo.save(customerDetail);
	        mockMvc.perform(MockMvcRequestBuilders
	                .delete("/deleteCustomerByCustomerName/Ram")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());
	    }
	
}
