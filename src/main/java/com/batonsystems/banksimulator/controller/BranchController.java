package com.batonsystems.banksimulator.controller;

import com.batonsystems.banksimulator.constants.Constants;
import com.batonsystems.banksimulator.entity.Branch;
import com.batonsystems.banksimulator.entity.SortOrder;
import com.batonsystems.banksimulator.exception.BranchException;
import com.batonsystems.banksimulator.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//This BranchController class is responsible to send and receive data related to Branch entity.
@RestController
public class BranchController {
    @Autowired
    private BranchService branchService;

    @PostMapping("/addBranch/{bankId}")
    public ResponseEntity<?> addBranch(@RequestBody Branch branch,@PathVariable long bankId) throws BranchException{
        try {
        	branchService.addBranch(branch,bankId);
            return ResponseEntity.ok(Constants.BRANCH_ADDED_SUCCESS_RESPONSE);
        }
        catch (BranchException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body("Something Went Wrong!");
        }
    }

    @GetMapping("/getBranches")
    public ResponseEntity<?> getBranches() throws BranchException{
        return ResponseEntity.ok().body(branchService.getBranches());
    }

    @GetMapping("/getBranchByBranchCode/{branchCode}")
    public ResponseEntity<?> getBranchByBranchCode(@PathVariable String branchCode){
        try {
            return ResponseEntity.ok().body(branchService.getBranchByBranchCode(branchCode));
        }
        catch (BranchException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body("Something Went Wrong!");
        }
    }

    @PostMapping("/getBranchesBySortOrder/{field}")
    public ResponseEntity<?> getBranchesBySortOrder(@PathVariable String field, @RequestBody SortOrder sortOrder){
        try {
            return ResponseEntity.ok().body(branchService.getBranchesBySortOrder(field,sortOrder));
        }
        catch (BranchException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body("Something Went Wrong!");
        }
    }

    @GetMapping("/getBranchesByCity/{branchCity}")
    public ResponseEntity<?> getBranchesByCity(@PathVariable String branchCity) {
        try {
            return ResponseEntity.ok().body(branchService.getBranchesByCity(branchCity));
        }
        catch (BranchException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body("Something Went Wrong!");
        }
    }


    @PutMapping("/updateBranch/{branchCode}")
    public ResponseEntity<?> updateBranch(@PathVariable String branchCode,@RequestBody Branch branch) {
        try {
        	branchService.updateBranch(branch);
            return ResponseEntity.ok(Constants.BRANCH_UPDATED_SUCCESS_RESPONSE);
        }
        catch (BranchException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body("Something Went Wrong!");
        }
    }

    @PutMapping("/activateBranch/{branchCode}")
    public ResponseEntity<?> activateBranch(@PathVariable String branchCode){
        try {
            return ResponseEntity.ok().body(branchService.activateBranch(branchCode));
        }
        catch (BranchException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body("Something Went Wrong!");
        }
    }

    @PutMapping("/deactivateBranch/{branchCode}")
    public ResponseEntity<?> deactivateBranch(@PathVariable String branchCode){
        try {
            return ResponseEntity.ok().body(branchService.deactivateBranch(branchCode));
        }
        catch (BranchException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body("Something Went Wrong!");
        }
    }

    @DeleteMapping("/deleteBranch/{branchCode}")
    public ResponseEntity<?> deleteBranchByBranchCode(@PathVariable String branchCode) throws Exception{
        try {
            branchService.deleteBranchByBranchCode(branchCode);
            return ResponseEntity.ok(Constants.BRANCH_DELETED_SUCCESS_RESPONSE);
        }
        catch (BranchException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body("Something Went Wrong!");
        }
    }
}
