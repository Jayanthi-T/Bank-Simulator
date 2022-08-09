package com.batonsystems.banksimulator.Integerationtest;

import com.batonsystems.banksimulator.constants.Constants;
import com.batonsystems.banksimulator.controller.BranchController;
import com.batonsystems.banksimulator.entity.Branch;
import com.batonsystems.banksimulator.exception.BranchException;
import com.batonsystems.banksimulator.repository.BranchRepository;
import com.batonsystems.banksimulator.service.BranchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
//        (classes = {BranchControllerTest.class})
//@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
//@WebMvcTest(BranchController.class)
//@RunWith(SpringRunner.class)
public class BranchControllerTest {

    @Autowired
    private MockMvc mockMvc;
//
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private BranchService branchService;
//    @MockBean
//    private BranchRepository branchRepository;
//
//    @InjectMocks
//    private BranchController branchController;

    @Autowired
    BranchRepository branchRepository;
    Branch branch_1 = new Branch("005943","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
    Branch branch_2 = new Branch("005922","Main Branch","SBIN0005922","123456722","SBININBBXXX","Coimbatore","Main Road");


    @Test
    @DirtiesContext
    public void testCreateBranchWhenInputIsNotNull() throws Exception{

        Branch branch = new Branch("005999","New Branch","SBIN0005999","123456789","SBININBBXXX","Mysore","Bazaar street");

//        Mockito.when(branchService.addBranch(any(Branch.class))).thenReturn(branch);
        branchRepository.save(branch);

        String newBranchDetail = objectMapper.writeValueAsString(branch);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/addBranch/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(newBranchDetail);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Constants.BRANCH_ADDED_SUCCESS_RESPONSE));
    }


    @Test
    @DirtiesContext
    public void testGetBranchesWhenBranchesAreNotEmpty() throws Exception{
        List<Branch> branches = new ArrayList<>(Arrays.asList(branch_1,branch_2));

//        Mockito.when(branchService.getBranches()).thenReturn(branches);
        branchRepository.saveAll(branches);

        mockMvc.perform(MockMvcRequestBuilders.get("/getBranches")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].branchName").value("Main Branch"));
    }

    @Test
    @DirtiesContext
    public void testGetBranchByBranchCodeWhenBranchCodeExists() throws Exception{
//        Mockito.when(branchService.getBranchByBranchCode(branch_1.getBranchCode())).thenReturn(branch_1);
        branchRepository.save(branch_1);

        mockMvc.perform(MockMvcRequestBuilders.get("/getBranchByBranchCode/005943")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.branchName").value("GCT Branch"));
    }


    @Test
    public void testGetBranchByBranchCodeWhenBranchCodeDoesNotExists() throws BranchException {

//        Mockito.when(branchService.getBranchByBranchCode("1234656")).thenThrow(new BranchException("Such Branch code doesn't exist."));
        Assertions.assertThrows(BranchException.class,()->branchService.getBranchByBranchCode("1234656"),"Such Branch code doesn't exist.");
    }

    @Test
    public void testGetBranchByBranchCodeWhenBranchCodeIsNull() throws BranchException {

//        Mockito.when(branchService.getBranchByBranchCode("")).thenThrow(new BranchException("Branch code can not be null or empty."));
        Assertions.assertThrows(BranchException.class,()->branchService.getBranchByBranchCode(""),"Branch code can not be null or empty.");
    }

    @Test
    @DirtiesContext
    public void testUpdateBranchWhenBranchCodeExists() throws Exception{
        Branch updatedBranch = new Branch("005943","PSG-GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Near PSG Campus");

//        Mockito.when(branchService.updateBranch(any(Branch.class))).thenReturn(updatedBranch);
        branchRepository.save(updatedBranch);
        String updatedBranchDetail = objectMapper.writeValueAsString(updatedBranch);

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.put("/updateBranch/005943")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedBranchDetail);

        mockMvc.perform(mockReq)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Constants.BRANCH_UPDATED_SUCCESS_RESPONSE));
    }

    @Test
    @DirtiesContext
    public void testDeleteBranchWhenBranchCodeExists() throws Exception{
        Branch branch2 = new Branch("005922","Main Branch","SBIN0005922","123456722","SBININBBXXX","Coimbatore","Main Road");

//        Mockito.when(branchService.getBranchByBranchCode(branch_2.getBranchCode())).thenReturn(branch_2);
        branchRepository.save(branch2);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/deleteBranch/005922")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
