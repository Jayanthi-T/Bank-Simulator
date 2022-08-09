
package com.batonsystems.banksimulator.service;

import java.util.List;

import com.batonsystems.banksimulator.dto.AccountDto;
import com.batonsystems.banksimulator.entity.Account;
import com.batonsystems.banksimulator.exception.AccountException;

public interface AccountService {

	public Account addAccount(Account newAccount, String branchCode) throws AccountException;

	public Account updateAccount(Account updateAccount) throws AccountException;

	public void deleteAccount(String accountNumber) throws AccountException;
	
	public List<AccountDto> getAccounts();

	public AccountDto getAccount(String accountNumber) throws AccountException;

	public void activateAccount(String accountNumber) throws AccountException;

	public void deactivateAccount(String accountNumber) throws AccountException;

	public AccountDto getAccountByAccountHolder(String accountHolderName) throws AccountException;



}
