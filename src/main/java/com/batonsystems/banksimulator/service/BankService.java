package com.batonsystems.banksimulator.service;

import com.batonsystems.banksimulator.dto.BankDto;
import com.batonsystems.banksimulator.entity.Bank;
import com.batonsystems.banksimulator.exception.BankException;

import java.util.List;

public interface BankService {

    List<BankDto> getAllBanks() throws BankException;

    BankDto getBankByCode(String code) throws BankException;

    Bank addBank(Bank newBank) throws BankException;

    Bank updateBank(Bank updateBank) throws BankException;

    void  deleteBankByCode(String code) throws BankException;

    boolean isBankExists(String code) throws BankException;

}
