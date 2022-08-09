
package com.batonsystems.banksimulator.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    @Column(name="isActive")
    private boolean isActive;

    @Column(name="accountNumber",unique=true)
    private String accountNumber;

    @Column(name = "accountHolderName")
    private String accountHolderName;

    @Column(name= "accountType")
    private String accountType;

    @Column(name= "balance")
    private float balance;


//    @OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="account")
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_asset_id")
    private Asset asset;
    
	@ManyToOne
	@JoinColumn(name="fk_customer_id" ,referencedColumnName = "customerId")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="fk_branch_Code",referencedColumnName="branchCode")
	private Branch branch;



    public Account() {
        this.accountHolderName=null;
        this.accountType=null;
        this.balance=0;
        this.isActive=false;
    }
    
    public Account(long accountId, String accountNumber,  String accountHolderName, String accountType, float balance) {

        this.accountId = accountId;
        this.accountNumber=accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = balance;
    }


    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
		return customer;
	}

	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@JsonIgnore
	public Branch getBranch() {
		return branch;
	}
	
	@JsonIgnore
	public void setBranch(Branch branch) {
		this.branch = branch;
	}


}
