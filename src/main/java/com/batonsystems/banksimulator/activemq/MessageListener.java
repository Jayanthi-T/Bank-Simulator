package com.batonsystems.banksimulator.activemq;

import com.batonsystems.banksimulator.exception.SwiftMessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


import com.batonsystems.banksimulator.entity.SwiftMT202Format;
import com.batonsystems.banksimulator.exception.AccountException;
import com.batonsystems.banksimulator.exception.BranchException;
import com.batonsystems.banksimulator.service.SWIFTMT202Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.io.IOException;


@Component
public class MessageListener {

    @Autowired
    SWIFTMT202Service swiftmt202Service;
    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @JmsListener(destination = "${swift.mt202.request.path}")
    public void SWIFTMT202RequestListener(String msgDetails) throws JMSException, IOException,AccountException, SwiftMessageException {


            logger.info("MT202 request received from queue : {}",msgDetails);

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JSR310Module());
            SwiftMT202Format map = (SwiftMT202Format) mapper.readValue(msgDetails, SwiftMT202Format.class);

            swiftmt202Service.generateSWIFTMT202Message(map);

    }

}
