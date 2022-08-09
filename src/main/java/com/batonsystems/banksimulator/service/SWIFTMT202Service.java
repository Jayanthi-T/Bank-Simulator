package com.batonsystems.banksimulator.service;

import com.batonsystems.banksimulator.entity.SWIFTMT202Request;
import com.batonsystems.banksimulator.entity.SwiftMT202Format;
import com.batonsystems.banksimulator.exception.AccountException;
import com.batonsystems.banksimulator.exception.BranchException;
import com.batonsystems.banksimulator.exception.SwiftMessageException;


//This class generates SWIFT MT202 message from the basic information provided.
public interface SWIFTMT202Service {

    public String generateSWIFTMT202Message(SwiftMT202Format SWIFTMT202Format) throws AccountException, SwiftMessageException;

}
