package com.batonsystems.banksimulator.dto;

import com.batonsystems.banksimulator.entity.Account;


public class AccountDto {
	
    private long accountId;
    
    private boolean isActive;
    
    private String accountNumber;

    private String accountHolderName;

    private String accountType;
    
    private float balance;
    
    Branch branch;
    Asset asset;
    Customer customer;
   
    class Branch{
    	private Long branchId;
    	private String branchCode;
    	private String branchName;
    	private String ifscCode;
    	private String micrCode;
    	private String swiftCode;
    	private String branchCity;
    	private String branchAddress;
    	public boolean isActive = false;
    		
    	
    	public Long getBranchId() {
    		return branchId;
    	}

    	public void setBranchId(Long branchId) {
    		this.branchId = branchId;
    	}

    	public String getBranchCode() {
    		return branchCode;
    	}
    	
    	public void setBranchCode(String branchCode) {
    		this.branchCode = branchCode;
    	}
    	
    	public String getBranchName() {
    		return branchName;
    	}
    	
    	public void setBranchName(String branchName) {
    		this.branchName = branchName;
    	}
    	
    	public String getIfscCode() {
    		return ifscCode;
    	}

    	public void setIfscCode(String ifscCode) {
    		this.ifscCode = ifscCode;
    	}
    	
    	public String getMicrCode() {
    		return micrCode;
    	}
    	
    	public void setMicrCode(String micrCode) {
    		this.micrCode = micrCode;
    	}
    	
    	public String getSwiftCode() {
    		return swiftCode;
    	}
    	
    	public void setSwiftCode(String swiftCode) {
    		this.swiftCode = swiftCode;
    	}
    	
    	public String getBranchCity() {
    		return branchCity;
    	}
    	
    	public void setBranchCity(String branchCity) {
    		this.branchCity = branchCity;
    	}
    	
    	public String getBranchAddress() {
    		return branchAddress;
    	}
    	
    	public void setBranchAddress(String branchAddress) {
    		this.branchAddress = branchAddress;
    	}


    	public boolean isActive() {
    		return isActive;
    	}

    	public void setActive(boolean active) {
    		isActive = active;
    	}
    }
    
    class Asset{
    	private long assetId;
    	private int assetCode;
    	private String assetType;
    	
        public long getAssetId() {
    		return assetId;
    	}

    	public void setAssetId(long assetId) {
    		this.assetId = assetId;
    	}

    	public int getAssetCode() {
            return assetCode;
        }

        public void setAssetCode(int assetCode) {
            this.assetCode = assetCode;
        }

        public String getAssetType() {
            return assetType;
        }

        public void setAssetType(String assetType) {
            this.assetType = assetType;
        }
    }
    
    
    class Customer{
    	private long customerId;
    	private String customerName;
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

	public Branch getBranch() {
		return branch;
	}
	
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
    
    public static AccountDto convertToDto(Account account) {
    	AccountDto accountDto=new AccountDto();
    	Branch branch=accountDto.new Branch();
    	Customer customer=accountDto.new Customer();
    	Asset asset=accountDto.new Asset();
    	if(account.getCustomer()!=null) {
    		customer.setCustomerId(account.getCustomer().getCustomerId());
    		customer.setCustomerName(account.getCustomer().getCustomerName());
    		accountDto.setCustomer(customer);
    	}
    	if(account.getAsset()!=null) {
    		asset.setAssetCode(account.getAsset().getAssetCode());
    	    asset.setAssetId(account.getAsset().getAssetId());
    	    asset.setAssetType(account.getAsset().getAssetType());
    	    accountDto.setAsset(asset);
    	}
    	if(account.getBranch()!=null) {
    		branch.setBranchCity(account.getBranch().getBranchCity());
    	    branch.setBranchAddress(account.getBranch().getBranchAddress());
    	    branch.setBranchCode(account.getBranch().getBranchCode());
    	    branch.setBranchId(account.getBranch().getBranchId());
    	    branch.setBranchName(account.getBranch().getBranchName());
    	    branch.setIfscCode(account.getBranch().getIfscCode());
    	    branch.setMicrCode(account.getBranch().getMicrCode());
    	    branch.setSwiftCode(account.getBranch().getSwiftCode());
    	    branch.setActive(account.getBranch().isActive());
    	    accountDto.setBranch(branch);
    	}
    	accountDto.setAccountHolderName(account.getAccountHolderName());
    	accountDto.setAccountId(account.getAccountId());
    	accountDto.setAccountNumber(account.getAccountNumber());
    	accountDto.setAccountType(account.getAccountType());
    	accountDto.setBalance(account.getBalance());
    	accountDto.setIsActive(account.getIsActive());
    	return accountDto;
    }
}