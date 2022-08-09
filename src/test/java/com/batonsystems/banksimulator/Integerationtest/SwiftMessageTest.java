package com.batonsystems.banksimulator.Integerationtest;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.DirtiesContext;

import com.batonsystems.banksimulator.entity.Account;
import com.batonsystems.banksimulator.entity.Branch;
import com.batonsystems.banksimulator.repository.AccountRepository;
import com.batonsystems.banksimulator.repository.BranchRepository;

//To Test SwiftMessages
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwiftMessageTest {
	
    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private BranchRepository branchRepo;
    
    @Value("${mt900.message.queue}")
    private String mt900Message;
    
    @Value("${mt910.message.queue}")
    private String mt910Message;
    
    @Value("${mt202.message.queue}")
    private String mt202Message;
    
    private String message="{1:F01TRINUS33AXXX0000000000}{2:I202HSBCSGSGXXXXN2020}{3:{108:FXbdfca32a915d49}}{4:\n"
    		+ ":20:FXbdfca32a915d49\n"
    		+ ":21:NUS701360430136\n"
    		+ ":32A:210908SGD2568,38\n"
    		+ ":52A:HSBCSGSG\n"
    		+ ":53B:/141-523704-001\n"
    		+ ":57A:HSBCSGSGXXX\n"
    		+ ":58A:/141-251207-001\n"
    		+ "MRMDUS33\n"
    		+ ":72:/BNF/REF/Foreign Exch Everywhere\n"
    		+ "-}";


    
    @JmsListener(destination = "${mt202.message.queue}")
    public void handle(String newMessage) {
        this.jmsTemplate.convertAndSend(mt202Message, newMessage);
    }
    
    
  @Test	
  @DirtiesContext
  public void testGenerateMT900AndMT910() throws IOException {
	  Branch branchDetails1=new Branch("1","ByPass Branch","SBI0013N","3737","4374772","Bareilly","ByPass Road");
	  Branch branchDetails2=new Branch("2","ByPass Branch","SBI0013N","3737","4374774","Bareilly","ByPass Road");
	  branchRepo.save(branchDetails1);
	  branchRepo.save(branchDetails2);
	  Account accountDetails1 =new Account(2,"141-523704-001","Priyam","Savings",4878);
	  Account accountDetails2 =new Account(3,"141-251207-001","Priyam","Savings",4878);
	  accountDetails1.setIsActive(true);
	  accountDetails2.setIsActive(true);
	  accountDetails1.setBranch(branchDetails1);
	  accountDetails2.setBranch(branchDetails2);
	  accountRepo.save(accountDetails1);
	  accountRepo.save(accountDetails2);
	  this.jmsTemplate.convertAndSend(mt202Message, message);
	  this.jmsTemplate.setReceiveTimeout(10_000);
  	  String excpectedMT900Message4thBlock=":21:FXbdfca32a915d49\r\n"
  			+ ":25:141-523704-001\r\n"
  			+ ":32A:210908SGD2568,38\r\n"
  			+ ":52A:/141-251207-001\r\n"
  			+ "MRMDUS33\r\n"
  			+ ":72:/BNF/REF/Foreign Exch Everywhere\r\n"
  			+ "-}";
	  String expectedMT900MessageOtherBlocks="{1:F01TESTUS00AXXX0000000000}{2:I900TESTUS00XXXXN}{3:{108:FXbdfca32a915d49}}{4:";
	  String excpectedMT910Message4thBlock=":25:141-251207-001\r\n"
        		+ ":32A:210908SGD2568,38\r\n"
        		+ ":52A:/141-523704-001\r\n"
        		+ "HSBCSGSG\r\n"
        		+ ":72:BNF/4374774INS/ORIGINATOR//4374772\r\n"
        		+ "-}";	
	  String expectedMT910MessageOtherBlocks="{1:F01TESTUS00AXXX0000000000}{2:I910TESTUS00XXXXN}{3:{108:FXbdfca32a915d49}}{4:";
      String actualMT900Message=this.jmsTemplate.receiveAndConvert(mt900Message).toString();
      String actualMT910Message=this.jmsTemplate.receiveAndConvert(mt910Message).toString();
      assertAll(
    		  ()->assertTrue(actualMT900Message.contains(excpectedMT900Message4thBlock)),
    		  ()->assertTrue(actualMT900Message.contains(expectedMT900MessageOtherBlocks)),
    		  ()->assertTrue(actualMT910Message.contains(excpectedMT910Message4thBlock)),
    		  ()->assertTrue(actualMT910Message.contains(expectedMT910MessageOtherBlocks))
  			  );
	  }
}