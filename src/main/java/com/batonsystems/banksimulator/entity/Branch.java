package com.batonsystems.banksimulator.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Branch implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long branchId;

	private String branchCode;

	private String branchName;
	private String ifscCode;
	private String micrCode;
	private String swiftCode;
	private String branchCity;
	private String branchAddress;

	public boolean isActive = false;

	public Branch() {

	}

	public Branch(long branchId,String branchCode, String branchName, String ifscCode, String micrCode, String swiftCode,
				  String branchCity, String branchAddress) {
		this.branchId = branchId;
		this.branchCode = branchCode;
		this.branchName = branchName;
		this.ifscCode = ifscCode;
		this.micrCode = micrCode;
		this.swiftCode = swiftCode;
		this.branchCity = branchCity;
		this.branchAddress = branchAddress;
	}
	public Branch(String branchCode, String branchName, String ifscCode, String micrCode, String swiftCode,
				  String branchCity, String branchAddress) {

		this.branchCode = branchCode;
		this.branchName = branchName;
		this.ifscCode = ifscCode;
		this.micrCode = micrCode;
		this.swiftCode = swiftCode;
		this.branchCity = branchCity;
		this.branchAddress = branchAddress;
	}

	@ManyToOne
	@JoinColumn(name="fk_bank_id" ,referencedColumnName = "bankId")
	private Bank bank;


    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="fk_branch_Code", referencedColumnName="branchCode")
    private List<Account> accounts;


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
	
	@JsonIgnore
	public Bank getBank() {
		return bank;
	}

	@JsonIgnore
	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}



}