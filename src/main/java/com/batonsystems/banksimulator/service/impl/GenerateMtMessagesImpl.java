package com.batonsystems.banksimulator.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.batonsystems.banksimulator.activemq.Producer;
import com.batonsystems.banksimulator.entity.Account;
import com.batonsystems.banksimulator.entity.Branch;
import com.batonsystems.banksimulator.entity.MtMessage;
import com.batonsystems.banksimulator.exception.AccountException;
import com.batonsystems.banksimulator.exception.BranchException;
import com.batonsystems.banksimulator.exception.SwiftMessageException;
import com.batonsystems.banksimulator.repository.AccountRepository;
import com.batonsystems.banksimulator.service.GenerateMtMessages;
import com.prowidesoftware.swift.model.SwiftBlock3;
import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.field.Field108;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field21;
import com.prowidesoftware.swift.model.field.Field25;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.field.Field52A;
import com.prowidesoftware.swift.model.field.Field72;
import com.prowidesoftware.swift.model.mt.mt2xx.MT202;
import com.prowidesoftware.swift.model.mt.mt9xx.MT900;
import com.prowidesoftware.swift.model.mt.mt9xx.MT910;

//This class is Responsible to generate the MT900 AND MT910 Swift Messages.
//All the Core bussiness logic is applied in this class.
@Service
public class GenerateMtMessagesImpl implements GenerateMtMessages {
	
	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private Producer producer;
	
	@Value("${mt900.field72.message}")
	private String mt900Field72Message;
	
	Logger logger=LoggerFactory.getLogger(this.getClass().getSimpleName()); 

	@Override
	public String getMT900(MtMessage mt) {
		// TODO Auto-generated method stub
		MT900 mt900=new MT900();
		SwiftBlock3 block3=new SwiftBlock3();
		Field108 field108=new Field108();
		field108.setComponent1("FXbdfca32a915d49");
		block3.append(field108);
		SwiftBlock4 block4 = new SwiftBlock4();
		Field20 field20=new Field20();
		field20.setComponent1(mt.getMetaId());
		Field21 field21=new Field21();
		field21.setComponent1(mt.getTransId());
		Field25 field25 = new Field25();
		field25.setAccount(mt.getSenderAccountNumber());
		Field32A field32A = new Field32A();
		field32A.setDate(mt.getDate());
		field32A.setAmount(mt.getAmount());
		field32A.setCurrency(mt.getCurrency());
		Field52A field52A=new Field52A();
		field52A.setAccount(mt.getReceiverAccountNumber());
		field52A.setBIC(mt.getReceiverBIC());
		Field72 field72=new Field72();
		field72.setComponent1(mt900Field72Message);
		block4.append(field20,field21,field25,field32A,field52A,field72);
		mt900.getSwiftMessage().setBlock3(block3);
		mt900.getSwiftMessage().setBlock4(block4);
		return mt900.message();
	}
	
	@Override
	public String getMT910(MtMessage mt) throws AccountException, BranchException {
		// TODO Auto-generated method stub
		MT910 mt910=new MT910();
		SwiftBlock3 block3=new SwiftBlock3();
		Field108 field108=new Field108();
		field108.setComponent1("FXbdfca32a915d49");
		block3.append(field108);
		SwiftBlock4 block4 = new SwiftBlock4();
		Field20 field20=new Field20();
		field20.setComponent1(mt.getTransId());
		Field21 field21=new Field21();
		field21.setComponent1(mt.getMetaId());
		Field25 field25 = new Field25();
		field25.setAccount(mt.getReceiverAccountNumber());
		Field32A field32A = new Field32A();
		field32A.setDate(mt.getDate());
		field32A.setAmount(mt.getAmount());
		field32A.setCurrency(mt.getCurrency());
		Field52A field52A=new Field52A();
		field52A.setBIC(mt.getSenderBIC());
		field52A.setAccount(mt.getSenderAccountNumber());
		Account receiverAccountDetails=accountRepo.getByAccountNumber(mt.getReceiverAccountNumber());
		if(receiverAccountDetails==null) throw new AccountException("Input Fields Are Empty");
		Branch receiverBranchDetails=receiverAccountDetails.getBranch();
		if(receiverBranchDetails==null) throw new BranchException("Branch does not exist");
		String receiverSwiftCode=receiverBranchDetails.getSwiftCode();
		Account senderAccountDetails=accountRepo.getByAccountNumber(mt.getSenderAccountNumber());
		if(senderAccountDetails==null) throw new AccountException("Input Fields Are Empty");
		Branch senderBranchDetails=senderAccountDetails.getBranch();
		if(senderBranchDetails==null) throw new BranchException("Branch does not exist");
		String senderSwiftCode=senderBranchDetails.getSwiftCode();
		Field72 field72=new Field72();
		field72.setComponent1("BNF/"+receiverSwiftCode+"INS/ORIGINATOR//"+senderSwiftCode);
		block4.append(field20,field21,field25,field32A,field52A,field72);
		mt910.getSwiftMessage().setBlock3(block3);
		mt910.getSwiftMessage().setBlock4(block4);
		return mt910.message();
	}
	
	@Override
	public boolean validateMT202Message(MT202 mt) {
		String transId=mt.getField20().getComponent1();
		String amount=mt.getField32A().getAmount();
		String date=mt.getField32A().getDate();
		String currency=mt.getField32A().getCurrency();
		String receiverAccountNumber=mt.getField58A().getAccount();
		String senderAccountNumber=mt.getField53B().getAccount();
		String senderBIC=mt.getField52A().getBIC();
		String receiverBIC=mt.getField58A().getBIC();
		if(transId==null || transId.trim().isEmpty()) return false;
		if(amount==null || amount.trim().isEmpty()) return false;
		if(date==null || date.trim().isEmpty()) return false;
		if(currency==null || currency.trim().isEmpty()) return false;
		if(receiverAccountNumber==null || receiverAccountNumber.trim().isEmpty()) return false;
		if(senderAccountNumber==null || senderAccountNumber.trim().isEmpty()) return false;
		if(senderBIC==null || senderBIC.trim().isEmpty()) return false;
		if(receiverBIC==null || receiverBIC.trim().isEmpty()) return false;
		return true;
	}
	
	@Override
	public List<String> processMt202(MT202 mt) throws AccountException, BranchException, SwiftMessageException {
		// TODO Auto-generated method stub
		if(validateMT202Message(mt)){
		MtMessage mtMessage=new MtMessage();
		mtMessage.setMetaId(generateRandomStringOfFormat16x());
		mtMessage.setTransId(mt.getField20().getComponent1());
		mtMessage.setAmount(mt.getField32A().getAmount());
		mtMessage.setDate(mt.getField32A().getDate());
		mtMessage.setCurrency(mt.getField32A().getCurrency());
		mtMessage.setReceiverAccountNumber(mt.getField58A().getAccount());
		mtMessage.setSenderAccountNumber(mt.getField53B().getAccount());
		mtMessage.setSenderBIC(mt.getField52A().getBIC());
		mtMessage.setReceiverBIC(mt.getField58A().getBIC());
		String mt900Message=getMT900(mtMessage);	
		producer.sendMT900Message(mt900Message);
		logger.info("Sent MT900 Message with trans id:{} to the Producer in reference of MT202 trans id:{}",mtMessage.getTransId(),mt.getField20().getComponent1());
		mtMessage.setMetaId(generateRandomStringOfFormat16x());
		mtMessage.setTransId(generateRandomStringOfFormat16x());
		String mt910Message=getMT910(mtMessage);
		producer.sendMT900Message(mt900Message);
		logger.info("Sent MT910 Message with trans id:{} to the Producer in reference of MT202 trans id:{}",mtMessage.getTransId(),mt.getField20().getComponent1());
		List<String> mtMessages=new ArrayList<String>();
		mtMessages.add(mt900Message);
		mtMessages.add(mt910Message);
		return mtMessages;
		}
		else {
			throw new SwiftMessageException("Input Fields can't be empty!!");
		}
	}

	@Override
	public String generateRandomStringOfFormat16x() {
		// TODO Auto-generated method stub
		UUID uuid1 = UUID.randomUUID();
		String part1=uuid1.toString().substring(0,4);
		UUID uuid2 = UUID.randomUUID();
		String part2=uuid2.toString().substring(0,4);
		UUID uuid3 = UUID.randomUUID();
		String part3=uuid3.toString().substring(0,4);
		UUID uuid4 = UUID.randomUUID();
		String part4=uuid4.toString().substring(0,4);
		String randomString=part1+part2+part3+part4;
		return randomString;
	}
	
}