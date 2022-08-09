package com.batonsystems.banksimulator.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bankId;

    @Column(name = "bankCode")
    private String bankCode;
    @Column(name = "bankName")
    private String bankName;


    @OneToMany(cascade=CascadeType.ALL,fetch= FetchType.EAGER)
    @JoinColumn(name="fk_bank_id", referencedColumnName="bankId")
    private List<Branch> branches;

    public Bank(long bankId, String bankCode, String bankName) {
        this.bankId = bankId;
        this.bankCode = bankCode;
        this.bankName = bankName;
    }

    public Bank() {
    }

    public Bank(String bankCode, String bankName) {
        this.bankCode = bankCode;
        this.bankName = bankName;
    }

    public Bank(long bankId, String bankName) {
        this.bankId = bankId;
        this.bankName = bankName;
    }

    public Bank(String bankName) {
        this.bankName = bankName;
    }

    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

	public List<Branch> getBranches() {
		return branches;
	}

	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}
    
    
}