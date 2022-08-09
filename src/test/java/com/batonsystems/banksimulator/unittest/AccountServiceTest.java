package com.batonsystems.banksimulator.unittest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.batonsystems.banksimulator.entity.Account;
import com.batonsystems.banksimulator.entity.Asset;
import com.batonsystems.banksimulator.entity.Branch;
import com.batonsystems.banksimulator.entity.Customer;
import com.batonsystems.banksimulator.exception.AccountException;
import com.batonsystems.banksimulator.repository.AccountRepository;
import com.batonsystems.banksimulator.repository.AssetRepository;
import com.batonsystems.banksimulator.repository.BranchRepository;
import com.batonsystems.banksimulator.repository.CustomerRepository;
import com.batonsystems.banksimulator.service.impl.AccountServiceImpl;

//This AccountServiceTest class is responsible to test the functionality related to the account entity.
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AccountServiceTest {

	@Autowired
	public AccountRepository accountRepo;
	
	@Autowired 
	private CustomerRepository customerRepo;
	
	@Autowired 
	private AssetRepository assetRepo; 
	
	@Autowired
	private BranchRepository branchRepo;
	
	public AccountServiceImpl accountServiceImpl;

	@Test
	@DirtiesContext
	public void testAddAccountWhenAccountsAreNonEmpty() throws AccountException {

		Account newAccount=new Account(1,"4747","Priyam","Savings",6000);
		accountRepo.save(newAccount);
		assertAll(
		()->assertEquals("4747",accountServiceImpl.addAccount(newAccount,null).getAccountNumber()),
		()->assertEquals("Priyam",accountServiceImpl.addAccount(newAccount,null).getAccountHolderName()),
		()->assertEquals("Savings",accountServiceImpl.addAccount(newAccount,null).getAccountType()),
		()->assertEquals(6000,accountServiceImpl.addAccount(newAccount,null).getBalance())
		);
	}

	@Test
	@DirtiesContext
	public void testAddAccountWhenAccountsAreEmpty() throws AccountException {
		Account missingValuesAccount=new Account();
		missingValuesAccount.setAccountHolderName("Sakshi");
		missingValuesAccount.setAccountNumber("5478");
		accountServiceImpl.addAccount(missingValuesAccount,null);
		assertEquals(0,accountServiceImpl.getAccountByAccountHolder("Sakshi").getBalance());
	}

	@Test
	@DirtiesContext
	public void testUpdateAccounts() throws AccountException{

		Account newAccount=new Account(1,"4747","Priyam","Current",7000);

		accountRepo.save(newAccount);
		Account accountDetails=new Account(1,"4747","Priyam","Savings",6000);
		assertAll(
		()->assertEquals("4747",accountServiceImpl.updateAccount(accountDetails).getAccountNumber()),
		()->assertEquals("Priyam",accountServiceImpl.updateAccount(accountDetails).getAccountHolderName()),
		()->assertEquals("Savings",accountServiceImpl.updateAccount(accountDetails).getAccountType()),
		()->assertEquals(6000,accountServiceImpl.updateAccount(accountDetails).getBalance())
		);
	}

	@Test
	@DirtiesContext
	public void testUpdateAccountsForExcpetionHandling() throws AccountException{
		Account accountDetails=new Account();
		accountDetails.setAccountId(1);
		accountDetails.setAccountNumber("4747");
		accountDetails.setAccountType("Savings");
		accountDetails.setIsActive(true);
		accountDetails.setBalance(5000);
		assertThrows(AccountException.class,()->accountServiceImpl.updateAccount(accountDetails));
	}

	@Test
	@DirtiesContext
	public void testDeleteAccount() throws AccountException {
		Account accountDetails=new Account(1,"4747","Priyam","Savings",6000);
		accountRepo.save(accountDetails);
		accountServiceImpl.deleteAccount("4747");
		boolean isPresent=accountRepo.existsByAccountNumber("4747");
		assertEquals(false,isPresent);
	}


	@Test
	@DirtiesContext
	public void testDeleteAccountForExceptionalHandling() throws AccountException {
		assertThrows(AccountException.class,()->accountServiceImpl.deleteAccount("4747"));
	}

	@Test
	@DirtiesContext
	public void testGetAccount() throws AccountException {

		Account newAccount=new Account(1,"4747","Priyam","Savings",6000);
		accountRepo.save(newAccount);

		assertAll(
		()->assertEquals("4747",accountServiceImpl.getAccount("4747").getAccountNumber()),
		()->assertEquals("Priyam",accountServiceImpl.getAccount("4747").getAccountHolderName()),
		()->assertEquals("Savings",accountServiceImpl.getAccount("4747").getAccountType()),
		()->assertEquals(6000,accountServiceImpl.getAccount("4747").getBalance())
		);

	}

	@Test
	@DirtiesContext
	public void testGetAccountByAccountHolder() throws AccountException {
		Account newAccount=new Account(1,"4747","Priyam","Savings",6000);
		accountRepo.save(newAccount);
		assertEquals("4747",accountServiceImpl.getAccountByAccountHolder("Priyam").getAccountNumber());
	}


	@Test
	@DirtiesContext
	public void testGetAccountByAccountHolderForExceptionalHandling() throws AccountException {
		Account newAccount=new Account();
		accountRepo.save(newAccount);
		assertThrows(AccountException.class,()->accountServiceImpl.getAccountByAccountHolder(" "));
	}

	@Test
	@DirtiesContext
	public void testGetAllAccountsWhenAccountsAreNonEmpty(){
		List<Account> accountList=new ArrayList<Account>();
		accountList.add(new Account(1,"4747","Priyam","Savings",6000));
		accountList.add(new Account(2,"7575","Ram","Current",7900));
		accountRepo.saveAll(accountList);
		assertEquals(2,accountServiceImpl.getAccounts().size());
	}



	@Test
	@DirtiesContext
	public void testGetAllAccountsWhenAccountsAreEmpty(){
		List<Account> accountList=new ArrayList<Account>();
		accountList.add(new Account());
		accountList.add(new Account());
		accountList.add(new Account());
		accountRepo.saveAll(accountList);
		assertAll(
		()->assertEquals(3,accountServiceImpl.getAccounts().size())
		);
	}

	@Test
	@DirtiesContext
	public void testDeactivateAccountWhenAccountAlreadyDeactivated() throws AccountException{
		Account newAccount=new Account(1,"4747","Priyam","Savings",6000);
		accountRepo.save(newAccount);
		assertThrows(AccountException.class,()->accountServiceImpl.deactivateAccount("4747"));
	}

	@Test
	@DirtiesContext
	public void testActivateAccountWhenAccountAlreadyActivated() throws AccountException{
		Account newAccount=new Account(1,"4747","Priyam","Savings",6000);
		newAccount.setIsActive(true);
		accountRepo.save(newAccount);
		assertThrows(AccountException.class,()->accountServiceImpl.activateAccount("4747"));
	}

}