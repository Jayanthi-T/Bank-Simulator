package com.batonsystems.banksimulator.unittest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;

import com.batonsystems.banksimulator.dto.AssetDto;
import com.batonsystems.banksimulator.entity.Account;
import com.batonsystems.banksimulator.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import com.batonsystems.banksimulator.constants.Constants;
import com.batonsystems.banksimulator.entity.Asset;
import com.batonsystems.banksimulator.exception.AssetException;
import com.batonsystems.banksimulator.repository.AssetRepository;
import com.batonsystems.banksimulator.service.impl.AssetServiceImpl;

//This AssetServiceTest class is responsible to test the functionality related to the asset entity.
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AssetServiceTest {

	@Autowired
	public AssetRepository assetRepo;

	@Autowired
	AssetServiceImpl assetServiceImpl;

	@Test
	@DirtiesContext
	public void testAddAsset() throws AssetException {
		Asset newAsset=new Asset(1,123,"USD");
		assetServiceImpl.addAsset(newAsset);
		boolean isAssetTypePresent=assetRepo.existsByAssetCode(123);
		boolean isAssetCodePresent=assetRepo.existsByAssetType("USD");
		assertAll(
				()->assertNotNull(newAsset),
				()->assertEquals(true,isAssetTypePresent),
				()->assertEquals(true,isAssetCodePresent)
		);
	}

	@Test
	@DirtiesContext
	public void testUpdateAsset() throws AssetException {
		Asset newAsset=new Asset(1,124,"USD");
		assetServiceImpl.addAsset(newAsset);
		Asset assetDetails=new Asset(1,124,"INR");
		assetServiceImpl.updateAsset(assetDetails);
		assertEquals("INR",assetRepo.getByAssetCode(124).getAssetType());
	}

	@Test
	@DirtiesContext
	public void testDeleteAssetByAssetType() throws AssetException {
		Asset newAsset=new Asset(1,123,"INR");
		assetRepo.save(newAsset);
		assetServiceImpl.deleteAssetByAssetType("INR");
		boolean isPresent=assetRepo.existsByAssetType("INR");
		assertEquals(false,isPresent);
	}

	@Test
	@DirtiesContext
	public void testDeleteAssetByAssetTypeWhenAssetTypeNotPresent() throws AssetException {
		Asset newAsset=new Asset(1,123,"INR");
		assetRepo.save(newAsset);
		assertThrows(AssetException.class,()->assetServiceImpl.deleteAssetByAssetType("ARB"));
	}

	@Test
	@DirtiesContext
	public void testDeleteAssetByAssetCode() throws AssetException {
		Asset newAsset=new Asset(1,123,"INR");
		assetRepo.save(newAsset);
		assetServiceImpl.deleteAssetByAssetCode(123);
		boolean isPresent=assetRepo.existsByAssetCode(123);
		assertEquals(false,isPresent);
	}

	@Test
	@DirtiesContext
	public void testDeleteAssetByAssetCodeWhenAssetCodeNotPresent() throws AssetException {
		Asset newAsset=new Asset(1,123,"INR");
		assetRepo.save(newAsset);
		assertThrows(AssetException.class,()->assetServiceImpl.deleteAssetByAssetCode(125));
	}

	@Test
	@DirtiesContext
	public void testGetAllAssets(){
		List<Asset> assetList=new ArrayList<Asset>();
		Asset asset1 = new Asset(1,123,"USD");
		Asset asset2 = new Asset(2,124,"INR");
		assetList.add(asset1);
		assetList.add(asset2);

		assetRepo.saveAll(assetList);
		assertEquals(2,assetServiceImpl.getAssets().size());
	}


	@Test
	@DirtiesContext
	public void testGetSortedAscAssets() throws AssetException {
		List<Asset> assetList=new ArrayList<Asset>();
		Asset asset1 = new Asset(1,123,"USD");
		Asset asset2 = new Asset(2,124,"INR");
		assetList.add(asset1);
		assetList.add(asset2);

		assetRepo.saveAll(assetList);
		List<Asset> expectedAssetList=new ArrayList<Asset>(List.of(new Asset(2,124,"INR"), new Asset(1,123,"USD")));
		List<AssetDto> actualAssetList=assetServiceImpl.getSortedAssets(Constants.ASC,Constants.ASSET_TYPE);
		assertEquals(expectedAssetList.size(),actualAssetList.size());
		for(int i=0;i<expectedAssetList.size();i++) {
			boolean isAssetCodeMatching= expectedAssetList.get(i).getAssetCode() == actualAssetList.get(i).getAssetCode()?true:false;
			boolean isAssetTypeMatching=expectedAssetList.get(i).getAssetType() == actualAssetList.get(i).getAssetType()?true:false;
			boolean isAssetIdMatching=expectedAssetList.get(i).getAssetId() == actualAssetList.get(i).getAssetId()?true:false;
			assertAll(
					()->assertEquals(true,isAssetCodeMatching),
					()->assertEquals(true,isAssetTypeMatching),
					()->assertEquals(true,isAssetIdMatching)
			);
		}
	}


	@Test
	@DirtiesContext
	public void testGetSortedDescAssets() throws AssetException {
		List<Asset> assetList=new ArrayList<Asset>();
		Asset asset1 = new Asset(1,123,"ARB");
		Asset asset2 = new Asset(2,124,"INR");
		assetList.add(asset1);
		assetList.add(asset2);

		assetRepo.saveAll(assetList);

		List<Asset> expectedAssetList=new ArrayList<Asset>(List.of(new Asset(2,124,"INR"),new Asset(1,123,"ARB")));
		List<AssetDto> actualAssetList=assetServiceImpl.getSortedAssets(Constants.DESC,Constants.ASSET_TYPE);
		assertEquals(expectedAssetList.size(),actualAssetList.size());
		for(int i=0;i<expectedAssetList.size();i++) {
			boolean isAssetCodeMatching= expectedAssetList.get(i).getAssetCode() == actualAssetList.get(i).getAssetCode()?true:false;
			boolean isAssetTypeMatching=expectedAssetList.get(i).getAssetType() == actualAssetList.get(i).getAssetType()?true:false;
			boolean isAssetIdMatching=expectedAssetList.get(i).getAssetId() == actualAssetList.get(i).getAssetId()?true:false;
			assertAll(
					()->assertEquals(true,isAssetCodeMatching),
					()->assertEquals(true,isAssetTypeMatching),
					()->assertEquals(true,isAssetIdMatching)
			);
		}
	}

	@Test
	@DirtiesContext
	public  void testGetAssetByAssetType() throws AssetException {
		Asset assetDetails=new Asset(1,124,"INR");
		assetRepo.save(assetDetails);
		assertEquals(124,assetServiceImpl.getAssetByAssetType("INR").getAssetCode());
	}

	@Test
	@DirtiesContext
	public  void testGetAssetByAssetCode() throws AssetException {
		Asset assetDetails=new Asset(1,124,"INR");
		assetRepo.save(assetDetails);
		assertEquals("INR",assetServiceImpl.getAssetByAssetCode(124).getAssetType());
	}

}