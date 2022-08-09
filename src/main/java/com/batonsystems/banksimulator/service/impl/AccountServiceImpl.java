
package com.batonsystems.banksimulator.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batonsystems.banksimulator.dto.AccountDto;
import com.batonsystems.banksimulator.entity.Account;
import com.batonsystems.banksimulator.entity.Branch;
import com.batonsystems.banksimulator.exception.AccountException;
import com.batonsystems.banksimulator.repository.AccountRepository;
import com.batonsystems.banksimulator.repository.BranchRepository;
import com.batonsystems.banksimulator.service.AccountService;


//This AccountServiceImpl class holds the business logic for operations related to account entity.
//Performed CRUD Operation and Activation And Deactivation for account entity.
@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired 
	private AccountRepository accountRepo;
	
	@Autowired
	private BranchRepository branchRepo;
	
	Logger logger=LoggerFactory.getLogger(this.getClass().getSimpleName()); 

	
	//To Add New Account  
	@Override
	public Account addAccount(Account newAccount,String branchCode) throws AccountException {
		// TODO Auto-generated method stub
		if(newAccount==null) throw new AccountException("Input Fields Are Empty");
		if(newAccount.getAccountHolderName()==null || newAccount.getAccountHolderName().trim().isEmpty())
			throw new AccountException("Input Fields Are Empty");
		if(branchCode!=null){
		Branch branchDetails=branchRepo.findByBranchCode(branchCode);
		newAccount.setBranch(branchDetails);
		}
		Account account=accountRepo.save(newAccount);
		logger.info("Account Added. account id:{}",newAccount.getAccountId());
		return account;
	}
	
	//To Update The Existing Account
	public Account updateAccount(Account updateAccount) throws AccountException {
		// TODO Auto-generated method stub
		if(updateAccount==null) throw new AccountException("Input Fields Are Empty");
		if(updateAccount.getAccountHolderName()==null || updateAccount.getAccountHolderName().trim().isEmpty())
			throw new AccountException("Input Fields Are Empty");
		Account account=accountRepo.save(updateAccount);
		logger.info("Account Updated. account id:{}",updateAccount.getAccountId());
		return account;
	}

	//To Delete The Account Using Account Number
	@Override
	public void deleteAccount(String accountNumber) throws AccountException {
		// TODO Auto-generated method stub
		Account accountDetail=accountRepo.getByAccountNumber(accountNumber);
		if(accountDetail==null) throw new AccountException("Account Number is not correct");
		accountRepo.delete(accountDetail);
		logger.info("Account Deleted. account number:{}",accountNumber);
	}
	
	//To Get Account Details Of A Particular Account
	public AccountDto getAccount(String accountNumber) throws AccountException{
		Account accountDetails= accountRepo.getByAccountNumber(accountNumber);
		if(accountDetails==null) throw new AccountException("Account Number is not correct"); 
		AccountDto accountDto=AccountDto.convertToDto(accountDetails);
		logger.info("Fetched Account. account id:{}",accountDto.getAccountId());
		return accountDto;
	}
	
	
	//To Get Account Details Of a Particular Account Holder
	@Override
	public AccountDto getAccountByAccountHolder(String accountHolderName) throws AccountException {
		// TODO Auto-generated method stub
		if(accountHolderName==null || accountHolderName.trim().isEmpty()) throw new AccountException("Account Holder Name can't be empty");
		Account account= accountRepo.getByAccountHolderName(accountHolderName);
		if(account==null) throw new AccountException("Account Name is Incorrect!!");
		AccountDto accountDto=AccountDto.convertToDto(account);
		logger.info("Fetched Account. account id:{}",accountDto.getAccountId());
		return accountDto;
	}

	//To Get All The Accounts 
	@Override
	public List<AccountDto> getAccounts(){
		// TODO Auto-generated method stub
		List<AccountDto> accountDtoList= new ArrayList<AccountDto>();
		List<Account> accountList=accountRepo.findAll();
		for(Account account:accountList) {
			AccountDto accountDto=AccountDto.convertToDto(account);
			accountDtoList.add(accountDto);
		}
		logger.info("Fetched All The Accounts");
		return accountDtoList;
	}

	//To Activate The Account
	@Override
	public void activateAccount(String accountNumber) throws AccountException{
		// TODO Auto-generated method stub
		Account updatedAccount=accountRepo.getByAccountNumber(accountNumber);
		if(updatedAccount==null) new AccountException("Account Number not present");
		if(updatedAccount.getIsActive()) throw new AccountException("Account Already Activated!!");
		updatedAccount.setIsActive(true);
		accountRepo.save(updatedAccount);
		logger.info("Activated Account. account id:{}",updatedAccount.getAccountId());
	}

	//To Deactivate The Account
	@Override
	public void deactivateAccount(String accountNumber) throws AccountException {
		// TODO Auto-generated method stub
		Account updatedAccount=accountRepo.getByAccountNumber(accountNumber);	
		if(updatedAccount==null) new AccountException("Account Number not present");
		if(!updatedAccount.getIsActive()) throw new AccountException("Account Already Deactivated!!");
		updatedAccount.setIsActive(false);
		accountRepo.save(updatedAccount);
		logger.info("Deactivated Account. account id:{}",updatedAccount.getAccountId());
	}

}