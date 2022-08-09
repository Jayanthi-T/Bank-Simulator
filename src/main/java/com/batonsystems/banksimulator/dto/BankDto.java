package com.batonsystems.banksimulator.dto;

import com.batonsystems.banksimulator.entity.Bank;
import com.batonsystems.banksimulator.entity.Branch;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BankDto {
    private long bankId;
    private String bankCode;
    private String bankName;

    List<Branches> branches;

    class Branches {
        private Long branchId;
        private String branchCode;
        private String branchName;
        private String ifscCode;
        private String micrCode;
        private String swiftCode;
        private String branchCity;
        private String branchAddress;
        public boolean isActive = false;

        public Long getBranchId() {
            return branchId;
        }

        public void setBranchId(Long branchId) {
            this.branchId = branchId;
        }

        public String getBranchCode() {
            return branchCode;
        }

        public void setBranchCode(String branchCode) {
            this.branchCode = branchCode;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getIfscCode() {
            return ifscCode;
        }

        public void setIfscCode(String ifscCode) {
            this.ifscCode = ifscCode;
        }

        public String getMicrCode() {
            return micrCode;
        }

        public void setMicrCode(String micrCode) {
            this.micrCode = micrCode;
        }

        public String getSwiftCode() {
            return swiftCode;
        }

        public void setSwiftCode(String swiftCode) {
            this.swiftCode = swiftCode;
        }

        public String getBranchCity() {
            return branchCity;
        }

        public void setBranchCity(String branchCity) {
            this.branchCity = branchCity;
        }

        public String getBranchAddress() {
            return branchAddress;
        }

        public void setBranchAddress(String branchAddress) {
            this.branchAddress = branchAddress;
        }


        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }
    }

    public List<Branches> getBranches() {
        return branches;
    }

    public void setBranches(List<Branches> branches) {
        this.branches = branches;
    }

    public static BankDto convertToBankDto(Bank bank){
        BankDto bankDto = new BankDto();
        bankDto.setBankId(bank.getBankId());
        bankDto.setBankCode(bank.getBankCode());
        bankDto.setBankName(bank.getBankName());

        if (bank.getBranches() != null) {
            Branches branches = bankDto.new Branches();
            List<Branches> branchesList = new ArrayList<>();

            for (Branch branch : bank.getBranches()) {
                branches.setBranchId(branch.getBranchId());
                branches.setBranchCode(branch.getBranchCode());
                branches.setBranchName(branch.getBranchName());
                branches.setIfscCode(branch.getIfscCode());
                branches.setMicrCode(branch.getMicrCode());
                branches.setSwiftCode(branch.getSwiftCode());
                branches.setBranchCity(branch.getBranchCity());
                branches.setBranchAddress(branch.getBranchAddress());
                branches.setActive(branch.isActive());
                branchesList.add(branches);
            }
            bankDto.setBranches(branchesList);
        }
        return bankDto;
    }

}
