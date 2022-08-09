package com.batonsystems.banksimulator.service.impl;


import com.batonsystems.banksimulator.entity.Bank;
import com.batonsystems.banksimulator.entity.Branch;
import com.batonsystems.banksimulator.entity.SortOrder;
import com.batonsystems.banksimulator.exception.BranchException;
import com.batonsystems.banksimulator.repository.BankRepository;
import com.batonsystems.banksimulator.repository.BranchRepository;
import com.batonsystems.banksimulator.service.BranchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.batonsystems.banksimulator.entity.SortOrder.*;

//This BranchServiceImpl class holds the business logic for operations related to Branch entity.
@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;
    
    @Autowired
    private BankRepository bankRepo;

    Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    //Add a new branch
    @Override
    public Branch addBranch(Branch branch,long bankId) throws BranchException{
    	if(branch==null) throw new BranchException("Branch can't be empty");
        if(branch.getBranchCode() == null){
            throw new BranchException("Branch code can not be null or empty.");
        }
        else {
        	Bank bankDetails=bankRepo.getByBankId(bankId);
        	branch.setBank(bankDetails);
            logger.info("Adding new branch: {}",branch.getBranchId(),branch.getBranchName());
            return branchRepository.save(branch);
        }
    }

    //Gets and displays all the branches
    @Override
    public List<Branch> getBranches() throws BranchException{
            return branchRepository.findAll();
    }

    //Gets and display the particular branch details by branchCode.
    @Override
    public Branch getBranchByBranchCode(String branchCode) throws BranchException{
        if (branchCode == null) {
            throw new BranchException("Branch code can not be null or empty.");
        }
        else if(!isBranchExists(branchCode)){
            throw new BranchException("Such Branch code doesn't exist.");
        }
        else {
            logger.info("Getting the branch by branchCode for {}",branchCode);
            return branchRepository.findByBranchCode(branchCode);
        }
    }

    //Gets branches by sortOrder of ascending or descending
    @Override
    public List<Branch> getBranchesBySortOrder(String field, SortOrder sortOrder) throws BranchException{

        if(sortOrder.getSortOrder().isEmpty() || field.isEmpty()){
            throw new BranchException("Field or sortOrder cannot be empty.");
        }
        else if (sortOrder.getSortOrder().equalsIgnoreCase(ASC)){
            logger.info("Getting list of branches in Ascending order.");
            List<Branch> ascSorted = branchRepository.findAll(Sort.by(Sort.Direction.ASC, field));
            return ascSorted;
        }
        else if (sortOrder.getSortOrder().equalsIgnoreCase(DESC)) {
            logger.info("Getting list of branches in Descending order.");
            List<Branch> descSorted = branchRepository.findAll(Sort.by(Sort.Direction.DESC, field));
            return descSorted;
        }
        else {
            logger.info("Getting list of branches in no particular order.");
            List<Branch> notSorted = branchRepository.findAll();
            return notSorted;
        }
    }

    //gets and displays branches by branchCity
    @Override
    public List<Branch> getBranchesByCity(String branchCity) throws BranchException{

        if (branchCity.isEmpty()){
            throw new BranchException("Branch city cannot be empty.");
        }
        else {
            logger.info("Getting the branch by branchCity for {}",branchCity);
            return branchRepository.getBranchesByBranchCity(branchCity);
        }
    }

    //Updates a particular branch
    @Override
    public Branch updateBranch(Branch branch) throws BranchException{
        if(branch.getBranchCode() == null){
            throw new BranchException("Branch code can not be null or empty.");
        }
        else if (!isBranchExists(branch.getBranchCode())) {
            throw new BranchException("Such Branch code doesn't exist.");
        }
        else {
            logger.info("Updating the branch with branchCode {}",branch.getBranchCode());
            return branchRepository.save(branch);
        }
    }

    //Activate a particular branch by branchCode
    @Override
    public Boolean activateBranch(String branchCode) throws BranchException{
        if (branchCode == null) {
            throw new BranchException("Branch code can not be null or empty.");
        }
        else if (!isBranchExists(branchCode)) {
            throw new BranchException("Such Branch code doesn't exist.");
        }
        else {
            Branch br = getBranchByBranchCode(branchCode);
            if (br.isActive) {
                Boolean activateMsg = false;
                logger.info("Branch with branchCode {} and branchId {} is already active.",branchCode,br.getBranchId());
                return activateMsg;
            } else {
                Boolean activateMsg;
                br.setActive(true);
                branchRepository.save(br);
                activateMsg = true;
                logger.info("Branch with branchCode {} and branchId {} is being activated.",branchCode,br.getBranchId());
                return activateMsg;
            }
        }
    }

    //Deactivate a particular branch by branchCode
    @Override
    public Boolean deactivateBranch(String branchCode) throws BranchException{
        if (branchCode == null) {
            throw new BranchException("Branch code can not be null or empty.");
        }
        else if (!isBranchExists(branchCode)) {
            throw new BranchException("Such Branch code doesn't exist.");
        }
        else {
            Branch br = getBranchByBranchCode(branchCode);
            Boolean deactivateMsg;

            if (br.isActive) {
                br.setActive(false);
                branchRepository.save(br);
                deactivateMsg = true;
                logger.info("Branch with branchCode {} and branchId {} is being de-activated.",branchCode,br.getBranchId());
            } else {
                logger.info("Branch with branchCode {} and branchId {} is already de-active.",branchCode,br.getBranchId());
                deactivateMsg = false;
            }
            return deactivateMsg;
        }
    }

    //checks if the branch with branch code exists
    @Override
    public boolean isBranchExists(String branchCode) throws BranchException{
        if (branchCode == null) {
            throw new BranchException("Branch code can not be null or empty.");
        }
        else {
            return branchRepository.existsByBranchCode(branchCode);
        }
    }

    //deletes particular branch by branch code
    @Transactional
    @Override
    public void deleteBranchByBranchCode(String branchCode) throws BranchException{
        if (branchCode == null) {
            throw new BranchException("Branch code can not be null or empty.");
        }
        else if (!isBranchExists(branchCode)) {
            throw new BranchException("Such Branch code doesn't exist.");
        }
        else {
            branchRepository.deleteByBranchCode(branchCode);
            logger.info("Deleted branch with branchCode {}",branchCode);
        }
    }
}
