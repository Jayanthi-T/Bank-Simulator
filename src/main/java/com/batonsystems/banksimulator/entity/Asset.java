
package com.batonsystems.banksimulator.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "assets")
public class Asset {
    @Id
    private long assetId;


    @Column(length = 3,name="assetCode")
    @Size(min = 3, max = 3)
    private int assetCode;

    @Column(name = "assetType")
    private String assetType;


    //    //Foreign key constraint
    @OneToOne(fetch= FetchType.EAGER,cascade=CascadeType.ALL,mappedBy="asset")
//    @JoinColumn(name="fk_account_id")
    private Account account;

    

    public Asset() {
        this.assetType=null;
    }

    public Asset(int assetId,int assetCode, String assetType) {
        this.assetId=assetId;
        this.assetCode = assetCode;
        this.assetType = assetType;
    }


    public long getAssetId() {
        return assetId;
    }

    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    public int getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(int assetCode) {
        this.assetCode = assetCode;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }



}