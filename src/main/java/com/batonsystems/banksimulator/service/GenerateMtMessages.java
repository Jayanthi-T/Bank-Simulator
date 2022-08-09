package com.batonsystems.banksimulator.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.batonsystems.banksimulator.entity.MtMessage;
import com.batonsystems.banksimulator.exception.AccountException;
import com.batonsystems.banksimulator.exception.BranchException;
import com.batonsystems.banksimulator.exception.SwiftMessageException;
import com.prowidesoftware.swift.model.mt.mt2xx.MT202;

public interface GenerateMtMessages {
	
	//Method is used to generate MT900 Swift Message
	public String getMT900(MtMessage mt);
	
	//Method is used to generate MT910 Swift Message
	public String getMT910(MtMessage mt) throws AccountException, BranchException;
	
	//Method to get Mt202 Swift Message and to send MT900 and MT910 Message To the respective queues
	public List<String> processMt202(MT202 mt) throws IOException, AccountException, BranchException, SwiftMessageException;
	
	//Method to Generate random 16x format
	public String generateRandomStringOfFormat16x();

	boolean validateMT202Message(MT202 mt);

}