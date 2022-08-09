package com.batonsystems.banksimulator.unittest;

import com.batonsystems.banksimulator.dto.BankDto;
import com.batonsystems.banksimulator.entity.Bank;
import com.batonsystems.banksimulator.exception.BankException;
import com.batonsystems.banksimulator.repository.BankRepository;
import com.batonsystems.banksimulator.service.impl.BankServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BankServiceTest {

    @Autowired
    private BankServiceImpl bankServiceImpl;

    @Autowired
    private BankRepository bankRepo;

    @Test
    @DirtiesContext
    public void testAddBankWhenBankIsNotNull() throws BankException {
        Bank bank=new Bank("1","State Bank Of India");
        bankServiceImpl.addBank(bank);
        assertAll(
        ()->assertEquals("State Bank Of India",bankRepo.findByBankCode("1").getBankName())
        );
    }

    @Test
    @DirtiesContext
    public void testAddBankWhenBankIsNull() throws BankException {
        Bank bank=new Bank();
        assertThrows(Exception.class,()->bankServiceImpl.addBank(bank));
    }

    @Test
    @DirtiesContext
    public void testGetBanks() throws BankException{
        List<Bank> bankList=new ArrayList<Bank>();
        bankList.add(new Bank("1","State Bank Of India"));
        bankList.add(new Bank("2","Central Bank Of India"));
        bankList.add(new Bank("3","Punjab National Bank"));
        bankRepo.saveAll(bankList);
        assertEquals(3,bankServiceImpl.getAllBanks().size());
    }

    @Test
    @DirtiesContext
    public void testGetBankByIdWhenIdIsNotNull() throws BankException{
        Bank bank = new Bank("7","State Bank Of India");
        bankRepo.save(bank);
        BankDto actual = bankServiceImpl.getBankByCode("7");
        assertEquals("State Bank Of India",actual.getBankName());
    }

    @Test
    @DirtiesContext
    public void testGetBankByIdWhenIdIsNull() throws BankException{
        assertThrows(Exception.class,()->bankServiceImpl.getBankByCode("1"));
    }

    @Test
    @DirtiesContext
    public void testDeleteBankByIdWhenIdIsNotNull() throws BankException{
        bankRepo.save(new Bank("1","State Bank Of India"));
        bankServiceImpl.deleteBankByCode("1");
        boolean isBankPresent=bankRepo.existsByBankCode("1");
        assertEquals(false,isBankPresent);
    }

    @Test
    @DirtiesContext
    public void testDeleteBranchByIdWhenIdIsNull() throws BankException{
        Bank bank = new Bank();
        assertThrows(Exception.class,()->bankServiceImpl.deleteBankByCode(bank.getBankCode()));
    }
}