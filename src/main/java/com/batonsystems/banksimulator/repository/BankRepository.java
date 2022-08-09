package com.batonsystems.banksimulator.repository;

import com.batonsystems.banksimulator.entity.Bank;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank,Long> {
    public  Bank findByBankCode(String code);
    public boolean existsByBankCode(String code);
    public void deleteByBankCode(String bcode);
    public Bank getByBankId(long bankId);
}
