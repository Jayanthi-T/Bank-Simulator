package com.batonsystems.banksimulator.unittest;

import com.batonsystems.banksimulator.entity.Account;
import com.batonsystems.banksimulator.entity.Branch;
import com.batonsystems.banksimulator.entity.SWIFTMT202Request;
import com.batonsystems.banksimulator.entity.SwiftMT202Format;
import com.batonsystems.banksimulator.exception.AccountException;
import com.batonsystems.banksimulator.exception.BranchException;
import com.batonsystems.banksimulator.exception.SwiftMessageException;
import com.batonsystems.banksimulator.repository.AccountRepository;
import com.batonsystems.banksimulator.repository.BranchRepository;
import com.batonsystems.banksimulator.service.SWIFTMT202Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


//Unit Tests for generateSWIFTMessage method
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class SWIFTMT202ServiceTest {

    @Autowired
    SWIFTMT202Service swiftmt202Service;
    @Autowired
    AccountRepository accountRepo;
    @Autowired
    BranchRepository branchRepository;

    //Tests generateSWIFTMessage method when AssetType is empty
    @Test
    @DirtiesContext
    public void testGenerateSWIFTMessageWhenAssetTypeIsEmpty() throws AccountException {

        Branch branch = new Branch("005943","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
        branchRepository.save(branch);

        Account acc1=new Account(1,"123456789","Jay","Savings",60000);
        acc1.setBranch(branch);
        accountRepo.save(acc1);
        Account acc2=new Account(2,"345678129","Ajay","Savings",30000);
        acc2.setBranch(branch);
        accountRepo.save(acc2);

//			LocalDate date = LocalDate.parse("2022-07-28");
        String date = "220814";
        SWIFTMT202Request trans = new SWIFTMT202Request("yes",null,1000,"123456789","345678129",date,"NUS7013604301361");
        SwiftMT202Format mt202Format = SwiftMT202Format.msgDetailsToFormat(trans);
        assertThrows(SwiftMessageException.class,()->swiftmt202Service.generateSWIFTMT202Message(mt202Format));

    }

    //Tests generateSWIFTMessage method when AccountNumber does not exist
    @Test
    @DirtiesContext
    public void testGenerateSWIFTMessageWhenAccountNumberDoesNotExist() throws AccountException{

//			LocalDate date = LocalDate.parse("2022-07-28");
        String date = "220814";
        SWIFTMT202Request trans = new SWIFTMT202Request("yes","INR",1000,"123456789","345678129",date,"NUS7013604301361");
        SwiftMT202Format mt202Format = SwiftMT202Format.msgDetailsToFormat(trans);
        assertThrows(AccountException.class,()->swiftmt202Service.generateSWIFTMT202Message(mt202Format));

    }

    // Tests generateSWIFTMessage method when ValueDate is empty
    @Test
    @DirtiesContext
    public void testGenerateSWIFTMessageWhenValueDateIsEmpty() throws AccountException{

        Branch branch = new Branch("005943","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
        branchRepository.save(branch);

        Account acc1=new Account(1,"123456789","Jay","Savings",60000);
        acc1.setBranch(branch);
        accountRepo.save(acc1);
        Account acc2=new Account(2,"345678129","Ajay","Savings",30000);
        acc2.setBranch(branch);
        accountRepo.save(acc2);

        String date = null;
        SWIFTMT202Request trans = new SWIFTMT202Request("yes","INR",1000,"123456789","345678129",date,"NUS7013604301361");
        SwiftMT202Format mt202Format = SwiftMT202Format.msgDetailsToFormat(trans);
        assertThrows(SwiftMessageException.class,()->swiftmt202Service.generateSWIFTMT202Message(mt202Format));

    }

    //Tests the generateSWIFTMessage method
    @Test
    @DirtiesContext
    public void testGenerateSWIFTMessage() throws AccountException, SwiftMessageException {
        Branch branch = new Branch("005943","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
        branchRepository.save(branch);

        Account acc1=new Account(1,"123456789001","Jay","Savings",60000);
        acc1.setBranch(branch);
        accountRepo.save(acc1);
        Account acc2=new Account(2,"345678129001","Ajay","Savings",30000);
        acc2.setBranch(branch);
        accountRepo.save(acc2);

        String date = "220814";
        SWIFTMT202Request trans = new SWIFTMT202Request("yes","INR",5000,"123456789001","345678129001",date,"NUS7013604301361");
        SwiftMT202Format mt202Format = SwiftMT202Format.msgDetailsToFormat(trans);
        String actual = swiftmt202Service.generateSWIFTMT202Message(mt202Format);

        String expected32A = ":32A:220814INR5000,";
        String expected53B = ":53B:/123456789001";
        String expected58A = ":58A:/345678129001";

        assertAll(
                ()->assertTrue(actual.contains(expected32A)),
                ()->assertTrue(actual.contains(expected53B)),
                ()->assertTrue(actual.contains(expected58A))
        );
    }

}
