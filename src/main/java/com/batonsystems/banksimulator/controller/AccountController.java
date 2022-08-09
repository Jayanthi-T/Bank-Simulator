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
import com.batonsystems.banksimulator.dto.AccountDto;
import com.batonsystems.banksimulator.entity.Account;
import com.batonsystems.banksimulator.exception.AccountException;
import com.batonsystems.banksimulator.service.AccountService;

//This AccountController class is responsible to create the endpoints and to send and receive request related to Account entity.
@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;


	//To Get Account Details Based On Account Number
	@GetMapping("/getAccount/{accountNumber}")
	public ResponseEntity<?> getAccount(@PathVariable String accountNumber) {
		try {
			return ResponseEntity.ok(accountService.getAccount(accountNumber));
		}
		catch(AccountException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
	
	//To Get All The Account Details 
	@GetMapping("/getAccounts")
	public List<AccountDto> getAccounts(){
			return accountService.getAccounts();
	}
	
	//TO Get Account Details Of a Particular Account Holder
	@GetMapping("/getAccountByAccountHolder/{accountHolderName}")
	public ResponseEntity<?> getAccountByAccountHolder(@PathVariable String accountHolderName) {
		try {
			return ResponseEntity.ok(accountService.getAccountByAccountHolder(accountHolderName));
		}
		catch(AccountException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
	
	// To Add An Account
	@PostMapping("/addAccount/{branchCode}")
	public ResponseEntity<?> addAccount(@RequestBody Account newAccount,@PathVariable String branchCode) {
		try {
			accountService.addAccount(newAccount,branchCode);
			return ResponseEntity.ok(Constants.ACCOUNT_ADDED_SUCCESS_RESPONSE);
		}
		catch(AccountException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
		catch(Exception exception) {
			exception.printStackTrace();
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
	
	//To Update AccountDetails of Existing Account
	@PutMapping("/updateAccount")
	public ResponseEntity<?> updateAccount(@RequestBody Account updateAccount) {
		try {
			accountService.updateAccount(updateAccount);
			return ResponseEntity.ok(Constants.ACCOUNT_UPDATED_SUCCESS_RESPONSE);
		}
		catch(AccountException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
	
	
	//To Delete A particular Account 
	@DeleteMapping("/deleteAccount/{accountNumber}")
	public ResponseEntity<?> deleteAccount(@PathVariable String accountNumber) {
		try {
			accountService.deleteAccount(accountNumber);
			return ResponseEntity.ok(Constants.ACCOUNT_DELETED_SUCCESS_RESPONSE);
		}
		catch(AccountException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
	
	//To Activate An Account
	@GetMapping("/activateAccount/{accountNumber}")
	public ResponseEntity<?> activateAccount(@PathVariable String accountNumber) {
		try {
			accountService.activateAccount(accountNumber);
			return ResponseEntity.ok(Constants.ACCOUNT_ACTIVATED);
		}
		catch(AccountException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
	
	//To Deactivate An Account
	@GetMapping("/deactivateAccount/{accountNumber}")
	public ResponseEntity<?> deactivateAccount(@PathVariable String accountNumber) {
		try {
			accountService.deactivateAccount(accountNumber);
			return ResponseEntity.ok(Constants.ACCOUNT_DEACTIVATED);
		}
		catch(AccountException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());			
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}

	
}