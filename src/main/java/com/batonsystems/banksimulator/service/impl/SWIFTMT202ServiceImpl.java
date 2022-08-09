package com.batonsystems.banksimulator.service.impl;

import com.batonsystems.banksimulator.activemq.MessageProducer;
import com.batonsystems.banksimulator.entity.Account;
import com.batonsystems.banksimulator.entity.Branch;
import com.batonsystems.banksimulator.entity.SwiftMT202Format;
import com.batonsystems.banksimulator.exception.AccountException;
import com.batonsystems.banksimulator.exception.BranchException;
import com.batonsystems.banksimulator.exception.SwiftMessageException;
import com.batonsystems.banksimulator.repository.AccountRepository;
import com.batonsystems.banksimulator.repository.BranchRepository;
import com.batonsystems.banksimulator.service.BranchService;
import com.batonsystems.banksimulator.service.SWIFTMT202Service;

import com.prowidesoftware.swift.model.*;
import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.mt2xx.MT202;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


//This class generates SWIFT MT202 message from the basic information provided.
@Service
public class SWIFTMT202ServiceImpl implements SWIFTMT202Service {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    MessageProducer producer;
    @Value("${swift.mt202.field72}")
    String field72Value;

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public Boolean validateInputs(SwiftMT202Format swiftmt202Format){

        if (swiftmt202Format == null){
            logger.error("Request for MT202 cannot be null.");
            return false;
        }

        Account senderAcc = accountRepository.getByAccountNumber(swiftmt202Format.getSenderAccountNumber());
        Branch senderBranch = senderAcc.getBranch();
        if (senderAcc == null || senderBranch == null) {
            logger.error("SenderAccount or SenderBranch cannot be null.");
            return false;
        }

        Account receiverAcc = accountRepository.getByAccountNumber(swiftmt202Format.getReceiverAccountNumber());
        Branch receiverBranch = receiverAcc.getBranch();
        if (receiverAcc == null || receiverBranch == null) {
            logger.error("ReceiverAccount or ReceiverBranch cannot be null.");
            return false;
        }

        if (senderBranch.getSwiftCode() == null || senderBranch.getSwiftCode().trim().isEmpty()) {
            logger.error("SenderBranch SwiftCode cannot be null or empty.");
            return false;
        }
        if (senderAcc.getAccountNumber() == null || senderAcc.getAccountNumber().trim().isEmpty()) {
            logger.error("SenderAccount AccountNumber cannot be null or empty.");
            return false;
        }
        if (receiverBranch.getSwiftCode() == null || receiverBranch.getSwiftCode().trim().isEmpty()) {
            logger.error("ReceiverBranch SwiftCode cannot be null or empty.");
            return false;
        }
        if (receiverAcc.getAccountNumber() == null || receiverAcc.getAccountNumber().trim().isEmpty()) {
            logger.error("ReceiverAccount AccountNumber cannot be null or empty.");
            return false;
        }
        if (receiverBranch.getBranchName() == null || receiverBranch.getBranchName().trim().isEmpty()) {
            logger.error("ReceiverBranch BranchName cannot be null or empty.");
            return false;
        }

        if (senderAcc.getAccountNumber() == receiverAcc.getAccountNumber()){
            logger.error("SenderAccountNumber and ReceiverAccountNumber cannot be same.");
            return false;
        }

        String transId = swiftmt202Format.getTransId();
        if (transId == null || transId.trim().isEmpty()) {
            logger.error("TransId cannot be null or empty.");
            return false;
        }

        String metaId = swiftmt202Format.getMetaId();
        if (metaId == null || metaId.trim().isEmpty()) {
            logger.error("MetaId cannot be null or empty.");
            return false;
        }

        if (field72Value == null || field72Value.trim().isEmpty()) {
            logger.error("Field72Value cannot be null or empty.");
            return false;
        }

        return true;
    }

    //Generates SWIFT MT202 message
    @Override
    public String generateSWIFTMT202Message(SwiftMT202Format swiftmt202Format) throws AccountException, SwiftMessageException {

        if(swiftmt202Format.getAssetType()==null || swiftmt202Format.getAssetType().trim().isEmpty()) throw new SwiftMessageException("Asset Type cannot be Null");
        else if(swiftmt202Format.getValueDate()==null || swiftmt202Format.getValueDate().trim().isEmpty()) throw new SwiftMessageException("Value Date cannot be Null");
        else if (swiftmt202Format.getMoney() <= 0) throw new SwiftMessageException("Money is invalid.");
        else if(!accountRepository.existsByAccountNumber(swiftmt202Format.getSenderAccountNumber()) || !accountRepository.existsByAccountNumber(swiftmt202Format.getReceiverAccountNumber()))
            throw new AccountException("Account Number is not correct");

        else {
            if (validateInputs(swiftmt202Format)) {
                Account senderAcc = accountRepository.getByAccountNumber(swiftmt202Format.getSenderAccountNumber());
                Branch senderBranch = senderAcc.getBranch();

                Account receiverAcc = accountRepository.getByAccountNumber(swiftmt202Format.getReceiverAccountNumber());
                Branch receiverBranch = receiverAcc.getBranch();

                MT202 MT202msg = new MT202();

                SwiftBlock3 bic3 = new SwiftBlock3();
                Field108 field108 = new Field108(swiftmt202Format.getTransId());
                bic3.append(field108);

                SwiftBlock4 bic4 = new SwiftBlock4();

                Field20 field20 = new Field20();
                field20.setComponent1(swiftmt202Format.getTransId());

                Field21 field21 = new Field21();
                field21.setComponent1(swiftmt202Format.getMetaId());

                Field32A field32A = new Field32A();
                field32A.setDate(swiftmt202Format.getValueDate());
                field32A.setComponent2(swiftmt202Format.getAssetType());
                field32A.setAmount(swiftmt202Format.getMoney());

                Field52A field52A = new Field52A();
                field52A.setBIC(senderBranch.getSwiftCode());

                Field53B field53B = new Field53B();
                field53B.setAccount(swiftmt202Format.getSenderAccountNumber());

                Field57A field57A = new Field57A();
                field57A.setBIC(receiverBranch.getSwiftCode() + receiverBranch.getBranchName());

                Field58A field58A = new Field58A();
                field58A.setAccount(swiftmt202Format.getReceiverAccountNumber());
                field58A.setComponent3(receiverBranch.getSwiftCode());

                Field72 field72 = new Field72();
                field72.setComponent1(field72Value);

                bic4.append(field20, field21, field32A, field52A, field53B, field57A, field58A, field72);

                MT202msg.getSwiftMessage().setBlock3(bic3);
                MT202msg.getSwiftMessage().setBlock4(bic4);

                logger.info("Sending MT 202 Message to response queue with transId- {}.",swiftmt202Format.getTransId());
                producer.sendMessage(MT202msg.message());

                if (swiftmt202Format.getIsOutputNeeded().contains("yes")) {
                    logger.info("Returning MT 202 Message to Postman with transId- {}.",swiftmt202Format.getTransId());
                    return MT202msg.message();
                } else {
                    return "View MT 202 Message in Response Queue.";
                }
            }
            else {
                logger.error("Input fields can't be null or empty.");
                throw new SwiftMessageException("Input fields can't be null or empty.");
            }
        }
    }

}
