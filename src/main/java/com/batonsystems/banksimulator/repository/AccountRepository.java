package com.batonsystems.banksimulator.repository;

import com.batonsystems.banksimulator.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;

//This AccountRepository class holds the database operations related to account entity.
public interface AccountRepository extends JpaRepository<Account,Long> {

	public Account getByAccountNumber(String accountNumber);
	public Account getByAccountHolderName(String accountHolderName);
	public boolean existsByAccountNumber(String accountNumber);
	public boolean existsByAccountHolderName(String accountHolderName);

}

