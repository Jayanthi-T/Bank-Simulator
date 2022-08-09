package com.batonsystems.banksimulator.entity;


import lombok.Getter;
import lombok.Setter;

//This class gives the transaction details that are required for generating SWIFT MT202 message.
@Setter
@Getter
public class SWIFTMT202Request {

    private String isOutputNeeded;

    private  String assetType;
    private  float money;
    private  String senderAccountNumber;
    private  String receiverAccountNumber;
    private  String valueDate;
    private String metaId;

    public SWIFTMT202Request() {
    }



    public SWIFTMT202Request(String isOutputNeeded, String assetType, float money, String senderAccountNumber, String receiverAccountNumber, String valueDate, String metaId) {
        this.isOutputNeeded = isOutputNeeded;
        this.assetType = assetType;
        this.money = money;
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.valueDate = valueDate;
        this.metaId = metaId;
    }

    public String getOutputNeeded() {
        return isOutputNeeded;
    }

    public void setOutputNeeded(String isOutputNeeded) {
        this.isOutputNeeded = isOutputNeeded;
    }

    public  String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public  float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public  String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public  String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public  String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getMetaId() {
        return metaId;
    }

    public void setMetaId(String metaId) {
        this.metaId = metaId;
    }
}
