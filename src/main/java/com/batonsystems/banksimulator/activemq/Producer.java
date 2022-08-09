package com.batonsystems.banksimulator.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

//This Producer Class is responsible to send MT900 and MT910 message.
@Component
public class Producer {
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Value("${mt900.message.queue}")
	private String mt900Message;
	
	@Value("${mt910.message.queue}")
	private String mt910Message;
	
	Logger logger=LoggerFactory.getLogger(Producer.class); 
	
	public void sendMT910Message(final String message) {
		jmsTemplate.convertAndSend(mt910Message,message);
	}	
	
	public void sendMT900Message(final String message) {
		jmsTemplate.convertAndSend(mt900Message,message);
	}

}
