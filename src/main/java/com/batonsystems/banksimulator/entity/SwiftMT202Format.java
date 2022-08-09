package com.batonsystems.banksimulator.entity;

import java.util.UUID;


//This class is used to generate an MT202 SWIFT message format
public class SwiftMT202Format {


    UUID uuid = UUID.randomUUID();
    String transId = "FX" + Long.toString(uuid.getMostSignificantBits(), 36);
    private String BIC3 = "{3:{108:"+transId+"}}";

    private String isOutputNeeded;
    private String valueDate ;
    public String assetType ;
    private float money ;
    private String metaId;

    private String senderAccountNumber ;
    private String receiverAccountNumber ;

    private String BIC4 = "{4:"+"\n"+":20:"+transId+"\n"+":21:NUS7013604301361"+"\n"+":32A:"+
            valueDate+assetType+money+"\n"+ ":52A:HSBCSGSGXXXX"+"\n"+":53B:/"+senderAccountNumber+"-001"+"\n"+
            ":57A:HSBCSGSGXXXX"+"\n"+":58A:/"+receiverAccountNumber+"-001"+"\n"+"MRMDUS33"+"\n"+":72:/BNF/REF/Foreign Exch Everywhere"+"\n"+"-}";

    public String getIsOutputNeeded() {
        return isOutputNeeded;
    }

    public void setIsOutputNeeded(String isOutputNeeded) {
        this.isOutputNeeded = isOutputNeeded;
    }

    public String getBIC3() {
        return BIC3;
    }
    public void setBIC3(String BIC3) {
        this.BIC3 = BIC3;
    }
    public String getBIC4() {
        return BIC4;
    }
    public void setBIC4(String BIC4) {
        this.BIC4 = BIC4;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public String getTransId() {
        return transId;
    }
    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getValueDate() {
        return valueDate;
    }
    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }
    public String getAssetType() {
        return assetType;
    }
    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }
    public float getMoney() {
        return money;
    }
    public void setMoney(float money) {
        this.money = money;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }
    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }
    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }
    public String getMetaId() {
        return metaId;
    }

    public void setMetaId(String metaId) {
        this.metaId = metaId;
    }
    public SwiftMT202Format() {
    }

    public static SwiftMT202Format msgDetailsToFormat(SWIFTMT202Request msgDetails){
        SwiftMT202Format swiftFormat = new SwiftMT202Format();
        swiftFormat.setIsOutputNeeded(msgDetails.getIsOutputNeeded());
        swiftFormat.setValueDate(msgDetails.getValueDate());
        swiftFormat.setAssetType(msgDetails.getAssetType());
        swiftFormat.setMoney(msgDetails.getMoney());

        swiftFormat.setSenderAccountNumber(msgDetails.getSenderAccountNumber());
        swiftFormat.setReceiverAccountNumber(msgDetails.getReceiverAccountNumber());
        swiftFormat.setMetaId(msgDetails.getMetaId());

//        swiftFormat.setBIC4( "{4:"+"\n"+":20:"+swiftFormat.getTransId()+"\n"+":21:NUS7013604301361"+"\n"+":32A:"+
//                swiftFormat.getValueDate()+swiftFormat.getAssetType()+swiftFormat.getMoney()+"\n"+ ":52A:HSBCSGSGXXXX"+"\n"+":53B:/"+swiftFormat.getSenderAc()+"-001"+"\n"+
//                ":57A:HSBCSGSGXXXX"+"\n"+":58A:/"+swiftFormat.getReceiverAc()+"-001"+"\n"+"MRMDUS33"+"\n"+":72:/BNF/REF/Foreign Exch Everywhere"+"\n"+"-}");


        return swiftFormat;
    }

}
