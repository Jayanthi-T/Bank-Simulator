package com.batonsystems.banksimulator.Integerationtest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.batonsystems.banksimulator.dto.AssetDto;
import com.batonsystems.banksimulator.entity.Account;
import com.batonsystems.banksimulator.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.batonsystems.banksimulator.constants.Constants;
import com.batonsystems.banksimulator.controller.AssetController;
import com.batonsystems.banksimulator.repository.AssetRepository;
import com.batonsystems.banksimulator.service.AssetService;
import com.batonsystems.banksimulator.entity.Asset;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@AutoConfigureMockMvc
public class AssetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private AssetService assetService;

    @MockBean
    private AssetRepository assetRepo;

    @InjectMocks
    private AssetController assetController;

    Asset assetDetails=new Asset(1,125,"ARB");

    @Test
    public void testGetAssets() throws Exception {

        List<Asset> assetList=new ArrayList<Asset>();
        Asset asset1 = new Asset(1,123,"USD");
        Asset asset2 = new Asset(2,124,"INR");
        assetList.add(asset1);
        assetList.add(asset2);

        List<AssetDto> assetDtoList = new ArrayList<>();
        for (Asset asset:assetList) {
            AssetDto assetDto = AssetDto.convertToAssetDto(asset);
            assetDtoList.add(assetDto);
        }

    	Mockito.when(assetService.getAssets()).thenReturn(assetDtoList);
    	mockMvc.perform(MockMvcRequestBuilders.get("/getAssets")
    	.contentType(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].assetType").value("INR"));
    }

    @Test
    public void testGetAssetByAssetType() throws Exception {

        Asset asset=new Asset(1,123,"USD");

        Mockito.when(assetService.getAssetByAssetType("USD")).thenReturn(AssetDto.convertToAssetDto(asset));

        mockMvc.perform(MockMvcRequestBuilders.get("/getAssetByAssetType/USD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assetCode").value(123));
    }

    @Test
    public void testGetAssetByAssetCode() throws Exception {

        Asset asset=new Asset(1,123,"USD");

        Mockito.when(assetService.getAssetByAssetCode(123)).thenReturn(AssetDto.convertToAssetDto(asset));

        mockMvc.perform(MockMvcRequestBuilders.get("/getAssetByAssetCode/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assetType").value("USD"));
    }


    @Test
    public void testAddAsset() throws Exception {
        Asset asset=new Asset(1,123,"USD");

        Mockito.when(assetService.addAsset(any(Asset.class))).thenReturn(asset);

        String assetDetail = objectMapper.writeValueAsString(asset);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/addAsset")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(assetDetail);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Constants.ASSET_ADDED_SUCCESS_RESPONSE));

    }

    @Test
    public void testUpdateAsset() throws Exception {
        Asset asset=new Asset(1,123,"USD");

        Mockito.when(assetService.updateAsset(any(Asset.class))).thenReturn(asset);

        String assetDetail = objectMapper.writeValueAsString(asset);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/updateAsset")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(assetDetail);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Constants.ASSET_UPDATED_SUCCESS_RESPONSE));
    }


    @Test
    public void testDeleteAssetByAssetType() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/deleteAssetByAssetType/USD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAssetByAssetCode() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/deleteAssetByAssetCode/125")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
