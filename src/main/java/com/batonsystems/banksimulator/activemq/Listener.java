package com.batonsystems.banksimulator.activemq;

import java.io.IOException;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.batonsystems.banksimulator.exception.AccountException;
import com.batonsystems.banksimulator.exception.BranchException;
import com.batonsystems.banksimulator.service.impl.GenerateMtMessagesImpl;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt2xx.MT202;

//This Listner Class is responsible to get input from MT202Message queue.
@Component
public class Listener {
	
	Logger logger=LoggerFactory.getLogger(Listener.class); 
	
	@Autowired
	public GenerateMtMessagesImpl generateMtMessagesImpl;

	@JmsListener(destination = "${mt202.message.queue}")
	public void receiveMessage(final String message) throws JMSException, IOException, AccountException, BranchException {
		try {
		AbstractMT msg = AbstractMT.parse(message);
		MT202 mt = (MT202) msg;
		logger.info("Received MT202 message from the queue with transID {}",mt.getField20().getComponent1());
		generateMtMessagesImpl.processMt202(mt);
		}
		catch(Exception e) {
			logger.error("Failed to process Mt202{}",e);
		}
	}
}
