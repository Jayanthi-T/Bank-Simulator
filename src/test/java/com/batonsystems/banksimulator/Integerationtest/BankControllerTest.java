package com.batonsystems.banksimulator.Integerationtest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.batonsystems.banksimulator.dto.BankDto;
import com.batonsystems.banksimulator.entity.Branch;
import com.batonsystems.banksimulator.repository.BranchRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.batonsystems.banksimulator.constants.Constants;
import com.batonsystems.banksimulator.controller.BankController;
import com.batonsystems.banksimulator.entity.Bank;
import com.batonsystems.banksimulator.entity.Customer;
import com.batonsystems.banksimulator.repository.BankRepository;
import com.batonsystems.banksimulator.service.BankService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private BankService bankService;

    @MockBean
    private BankRepository bankRepo;

    @InjectMocks
    private BankController bankController;

    Bank bank=new Bank("3","Punjab National Bank");

    @Test
    public void testGetBanks() throws Exception {
        List<Bank> bankList= new ArrayList<Bank>();

    	Bank bank1 = new Bank("1","Central Bank Of India");
    	Bank bank2 = new Bank("2","State Bank Of India");
        bankList.add(bank1);
        bankList.add(bank2);
        bankRepo.saveAll(bankList);

        List<BankDto> bankDtoList = new ArrayList<>();
        for (Bank bank:bankList) {
            BankDto bankDto = BankDto.convertToBankDto(bank);
            bankDtoList.add(bankDto);
        }

        Mockito.when(bankService.getAllBanks()).thenReturn(bankDtoList);
        mockMvc.perform(MockMvcRequestBuilders.get("/getBanks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bankName").value("State Bank Of India"));
   }

    @Test
    public void testAddBank() throws Exception {
        Bank bank=new Bank("1","State Bank Of India");

        Mockito.when(bankService.addBank(any(Bank.class))).thenReturn(bank);

        String bankDetail = objectMapper.writeValueAsString(bank);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/addBank")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(bankDetail);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Constants.BANK_ADDED_SUCCESS_RESPONSE));

    }

    @Test
    public void testUpdateBank() throws Exception {
        Bank bank=new Bank("1","State Bank Of India");

        Mockito.when(bankService.updateBank(any(Bank.class))).thenReturn(bank);

        String bankDetail = objectMapper.writeValueAsString(bank);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/updateBank")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(bankDetail);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Constants.BANK_UPDATED_SUCCESS_RESPONSE));
    }


    @Test
    public void deleteBankById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/deleteBank/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
