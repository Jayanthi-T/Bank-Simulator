package com.batonsystems.banksimulator.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${swift.mt202.response.path}")
    private String responseQueue;

    public void sendMessage(final String message) {
        jmsTemplate.convertAndSend(responseQueue,message);
    }

}
