package com.batonsystems.banksimulator.Integerationtest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.batonsystems.banksimulator.entity.Account;
import com.batonsystems.banksimulator.entity.Asset;
import com.batonsystems.banksimulator.entity.Branch;
import com.batonsystems.banksimulator.entity.Customer;
import com.batonsystems.banksimulator.repository.AccountRepository;
import com.batonsystems.banksimulator.repository.AssetRepository;
import com.batonsystems.banksimulator.repository.BranchRepository;
import com.batonsystems.banksimulator.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

//This AccountControllerTest is responsible to test the all the functionality related to account controller.
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired 
	private AccountRepository accountRepo;
	
	@Autowired 
	private CustomerRepository customerRepo;
	
	@Autowired 
	private AssetRepository assetRepo; 
	
	@Autowired 
	private BranchRepository branchRepo;
	
    @Test
    @DirtiesContext
    public void testGetAccount() throws Exception {
    	Account account=new Account(1,"4747","Priyam","Savings",6000);
    	Customer customer=new Customer(1,"Raj");
    	customerRepo.save(customer);
    	Asset asset= new Asset(1,123,"USD");
    	assetRepo.save(asset);
    	Branch branch=new Branch("1","Bareilly Branch","SBI007N","SBI76464","SBI83838","Bareilly","Pilibit ByPass Branch");
    	branchRepo.save(branch);
    	account.setCustomer(customer);
    	account.setAsset(asset);
    	account.setBranch(branch);
    	accountRepo.save(account);
    	
    	mockMvc.perform(MockMvcRequestBuilders.get("/getAccount/4747")
    	    	.contentType(MediaType.APPLICATION_JSON))
    	    	.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountHolderName").value("Priyam"));
    }

    @Test
    @DirtiesContext
    public void testGetAccounts() throws Exception {
    	
    	Account account1=new Account(1,"4747","Priyam","Savings",6000);
    	Customer customer1=new Customer(1,"Raj");
    	customerRepo.save(customer1);
    	Asset asset1= new Asset(1,123,"USD");
    	assetRepo.save(asset1);
    	Branch branch1=new Branch("1","Bareilly Branch","SBI007N","SBI76464","SBI83838","Bareilly","Pilibit ByPass Branch");
    	branchRepo.save(branch1);
    	account1.setCustomer(customer1);
    	account1.setAsset(asset1);
    	account1.setBranch(branch1);
    	accountRepo.save(account1);
    	Account account2=new Account(2,"4748","Ram","Savings",1000);
    	Customer customer2=new Customer(2,"Shree");
    	customerRepo.save(customer2);
    	Asset asset2= new Asset(1,123,"USD");
    	assetRepo.save(asset2);
    	Branch branch2=new Branch("2","Bareilly Branch","SBI007N","SBI76464","SBI83838","Bareilly","Pilibit ByPass Branch");
    	branchRepo.save(branch2);
    	account2.setCustomer(customer2);
    	account2.setAsset(asset2);
    	account2.setBranch(branch2);
    	accountRepo.save(account2);
    	
    	mockMvc.perform(MockMvcRequestBuilders.get("/getAccounts")
    	.contentType(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].accountHolderName").value("Ram"));
    }


    @Test
    @DirtiesContext
    public void testGetAccountByAccounHolder() throws Exception {

    	Account account=new Account(1,"4747","Priyam","Savings",6000);
    	Customer customer=new Customer(1,"Raj");
    	customerRepo.save(customer);
    	Asset asset= new Asset(1,123,"USD");
    	assetRepo.save(asset);
    	Branch branch=new Branch("1","Bareilly Branch","SBI007N","SBI76464","SBI83838","Bareilly","Pilibit ByPass Branch");
    	branchRepo.save(branch);
    	account.setCustomer(customer);
    	account.setAsset(asset);
    	account.setBranch(branch);
    	accountRepo.save(account);
    	

    	mockMvc.perform(MockMvcRequestBuilders.get("/getAccountByAccountHolder/Priyam")
    	    	.contentType(MediaType.APPLICATION_JSON))
    	    	.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(6000));
    }


    @Test
    @DirtiesContext
    public void testAddAccount() throws Exception {

        Account account=new Account(1,"4747","Priyam","Savings",6000);

        accountRepo.save(account);
        String accountDetail = objectMapper.writeValueAsString(account);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/addAccount/123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(accountDetail);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Constants.ACCOUNT_ADDED_SUCCESS_RESPONSE));

    }

    @Test
    @DirtiesContext
    public void testUpdateAccount() throws Exception {

        Account account=new Account(1,"4747","Priyam","Savings",6000);

        accountRepo.save(account);
        String accountDetail = objectMapper.writeValueAsString(account);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/updateAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(accountDetail);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Constants.ACCOUNT_UPDATED_SUCCESS_RESPONSE));
    }


    @Test
    @DirtiesContext
    public void testDeleteAccount() throws Exception {
    	Account accountDetail=new Account(3,"4749","Kishan","Savings",9000);
    	accountRepo.save(accountDetail);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/deleteAccount/4749")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    public void testActivateAccount() throws Exception {

        Account accountDetail=new Account(3,"4749","Kishan","Savings",9000);
        accountRepo.save(accountDetail);
        mockMvc.perform(MockMvcRequestBuilders.get("/activateAccount/4749")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Constants.ACCOUNT_ACTIVATED));

    }


}
