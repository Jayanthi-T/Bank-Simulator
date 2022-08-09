package com.batonsystems.banksimulator.controller;

import com.batonsystems.banksimulator.constants.Constants;
import com.batonsystems.banksimulator.dto.BankDto;
import com.batonsystems.banksimulator.entity.Bank;
import com.batonsystems.banksimulator.exception.BankException;
import com.batonsystems.banksimulator.service.BankService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//This BankController class is responsible to create the endpoints and to send and receive request related to Bank entity.
@RestController
public class BankController {

    @Autowired
    private BankService bankService;

    //To Get ALL THE BANKS
    @GetMapping("/getBanks")
    public ResponseEntity<?> listBanks() throws BankException {

        return ResponseEntity.ok().body(bankService.getAllBanks());
    }

    //To Get Bank Details Of Specific Bank Code
    @GetMapping("/getBankById/{code}")
    public ResponseEntity<?> getBank(String code) throws BankException
    {
        try {
            return ResponseEntity.ok().body(bankService.getBankByCode(code));
        }
        catch (BankException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body("Something Went Wrong!");
        }
    }

    //To Add A Bank
    @PostMapping("/addBank")
    public ResponseEntity<?> addBank(@RequestBody Bank newBank) throws BankException {
        try {
        	bankService.addBank(newBank);
             return ResponseEntity.ok(Constants.BANK_ADDED_SUCCESS_RESPONSE);
        }
        catch (BankException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body("Somethings Went Wrong!");
        }
    }

    //To Update A Bank
    @PutMapping("/updateBank")
    public ResponseEntity<?> updateBank(@RequestBody Bank updateBank) throws BankException {
        try {
        	bankService.updateBank(updateBank);
            return ResponseEntity.ok(Constants.BANK_UPDATED_SUCCESS_RESPONSE);
        }
        catch (BankException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body("Something Went Wrong!");
        }
    }

    //To Delete A Bank
    @DeleteMapping("deleteBank/{code}")
    public ResponseEntity<?> deleteBankById(@PathVariable String code) throws BankException{
        try {
            bankService.deleteBankByCode(code);
            return ResponseEntity.ok().body(Constants.BANK_DELETED_SUCCESS_RESPONSE);
        }
        catch (BankException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body("Something Went Wrong!");
        }
    }


}
