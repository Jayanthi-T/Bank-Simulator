package com.batonsystems.banksimulator.controller;

import com.batonsystems.banksimulator.entity.SWIFTMT202Request;
import com.batonsystems.banksimulator.entity.SwiftMT202Format;
import com.batonsystems.banksimulator.exception.AccountException;
import com.batonsystems.banksimulator.exception.BranchException;
import com.batonsystems.banksimulator.exception.SwiftMessageException;
import com.batonsystems.banksimulator.service.SWIFTMT202Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SWIFTMT202Controller {
    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${swift.mt202.response.path}")
    private String responseQueue;
    @Autowired
    private SWIFTMT202Service swiftmt202Service;

    //To generate SWIFT message for a corresponding transaction
    @PostMapping("/generateSWIFTMT202Message")
    public ResponseEntity<String> SWIFT202Message(@RequestBody SWIFTMT202Request swiftmt202Request) throws SwiftMessageException,AccountException {

        SwiftMT202Format requestMTFormat = SwiftMT202Format.msgDetailsToFormat(swiftmt202Request);

        String mt202Msg = swiftmt202Service.generateSWIFTMT202Message(requestMTFormat);
        return ResponseEntity.ok().body(mt202Msg);
    }

}
