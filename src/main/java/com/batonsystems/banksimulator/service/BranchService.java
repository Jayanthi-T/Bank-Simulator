package com.batonsystems.banksimulator.service;


import com.batonsystems.banksimulator.entity.Branch;
import com.batonsystems.banksimulator.entity.SortOrder;
import com.batonsystems.banksimulator.exception.BranchException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BranchService {
    Branch addBranch(Branch branch,long bankId) throws BranchException;
    List<Branch> getBranches() throws BranchException;
    Branch getBranchByBranchCode(String branchCode) throws BranchException;
    List<Branch> getBranchesBySortOrder(String field, SortOrder sortOrder) throws BranchException;
    List<Branch> getBranchesByCity(String branchCity) throws BranchException;
    Boolean activateBranch(String branchCode) throws BranchException;
    Boolean deactivateBranch(String branchCode) throws BranchException;
    Branch updateBranch(Branch branch) throws BranchException;
    boolean isBranchExists(String branchCode) throws BranchException;
    void deleteBranchByBranchCode(String branchCode) throws BranchException;
 }
