package com.batonsystems.banksimulator.dto;

import com.batonsystems.banksimulator.entity.Asset;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssetDto {

    private long assetId;
    private int assetCode;
    private String assetType;

   Account account;

    class Account{
        private long accountId;
        private boolean isActive;
        private String accountNumber;
        private String accountHolderName;
        private String accountType;
        private float balance;

        public boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }

        public long getAccountId() {
            return accountId;
        }

        public void setAccountId(long accountId) {
            this.accountId = accountId;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getAccountHolderName() {
            return accountHolderName;
        }

        public void setAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public float getBalance() {
            return balance;
        }

        public void setBalance(float balance) {
            this.balance = balance;
        }

    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public static AssetDto convertToAssetDto(Asset asset){
        AssetDto assetDto = new AssetDto();
        assetDto.setAssetId(asset.getAssetId());
        assetDto.setAssetCode(asset.getAssetCode());
        assetDto.setAssetType(asset.getAssetType());

        if(asset.getAccount() != null) {
            Account account = assetDto.new Account();
            account.setAccountId(asset.getAccount().getAccountId());
            account.setIsActive(asset.getAccount().getIsActive());
            account.setAccountNumber(asset.getAccount().getAccountNumber());
            account.setAccountType(asset.getAccount().getAccountType());
            account.setAccountHolderName(asset.getAccount().getAccountHolderName());
            account.setBalance(asset.getAccount().getBalance());

            assetDto.setAccount(account);
        }
        return assetDto;
    }

}
