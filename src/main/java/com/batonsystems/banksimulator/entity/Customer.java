package com.batonsystems.banksimulator.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Customer {
	
	@Id
	private long customerId;
	
	private String customerName;
	
    //Foreign key constraint
	@OneToMany(cascade=CascadeType.ALL)
	private List<Account> accounts;
	
	public Customer() {
		
	}
	
	public Customer(long customerId,String customerName) {
		this.customerId=customerId;
		this.customerName=customerName;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@JsonIgnore
	public List<Account> getAccounts() {
		return accounts;
	}

	@JsonIgnore
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	
	
}
