package com.batonsystems.banksimulator.service.impl;


import com.batonsystems.banksimulator.dto.BankDto;
import com.batonsystems.banksimulator.exception.BankException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batonsystems.banksimulator.entity.Bank;
import com.batonsystems.banksimulator.repository.BankRepository;
import com.batonsystems.banksimulator.service.BankService;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    private BankRepository bankRepo;
    
    Logger logger=LoggerFactory.getLogger(this.getClass().getSimpleName()); 

    @Override
    public List<BankDto> getAllBanks() throws BankException{
        List<BankDto> bankDtoList = new ArrayList<>();
        List<Bank> bankList =  bankRepo.findAll();
        for (Bank bank:bankList){
            BankDto bankDto = BankDto.convertToBankDto(bank);
            bankDtoList.add(bankDto);
        }
        logger.info("Fetched All Banks");
        return bankDtoList;
    }

    public BankDto getBankByCode(String code) throws BankException
    {
        if (code == null) {
            throw new BankException("Branch code can not be null or empty.");
        }
        else if(!isBankExists(code)){
            throw new BankException("Such Branch code doesn't exist.");
        }
        else {
            Bank bank = bankRepo.findByBankCode(code);
            BankDto bankDto = BankDto.convertToBankDto(bank);
            logger.info("Fetched Bank. bank id:{}",bankDto.getBankId());
            return bankDto;
        }
    }
    @Override
    public Bank addBank(Bank newBank) throws BankException {
        if(newBank.getBankCode() == null){
            throw new BankException("Branch code can not be null or empty.");
        }
        else {
        	logger.info("Bank Added. bankId:{}",newBank.getBankId());
            return bankRepo.save(newBank);
        }
    }

    @Override
    public Bank updateBank(Bank updateBank) throws BankException {

        if(updateBank.getBankCode() == null){
            throw new BankException("Bank code can not be null or empty.");
        }
        else if (!isBankExists(updateBank.getBankCode())) {
            throw new BankException("Such Branch code doesn't exist.");
        }
        else {
        	logger.info("Bank Updated. bank id:{}",updateBank.getBankId());
            return bankRepo.save(updateBank);
        }

    }



    //checks if the bank with bank name exists
    @Override
    public boolean isBankExists(String code) throws BankException {
        if(code.isEmpty()){
            throw  new BankException("Bank Name Cannot be Null");
        }
        else{
            return bankRepo.existsByBankCode(code);
        }
    }

    public void deleteBankByCode(String code) throws BankException{
        if (code == null) {
            throw new BankException("Branch code can not be null or empty.");
        }
        else if (!isBankExists(code)) {
            throw new BankException("Such Branch code doesn't exist.");
        }
        else {
        	Bank bankDetails = bankRepo.findByBankCode(code);
            bankRepo.delete(bankDetails);
            logger.info("Bank Deleted bank id:{}",bankDetails.getBankId());
        }
    }
}
