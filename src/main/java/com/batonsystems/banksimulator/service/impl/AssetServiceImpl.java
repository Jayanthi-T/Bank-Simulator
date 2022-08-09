package com.batonsystems.banksimulator.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.batonsystems.banksimulator.dto.AssetDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.batonsystems.banksimulator.constants.Constants;
import com.batonsystems.banksimulator.entity.Asset;
import com.batonsystems.banksimulator.exception.AssetException;
import com.batonsystems.banksimulator.repository.AssetRepository;
import com.batonsystems.banksimulator.service.AssetService;

//This AssetServiceImpl class holds the business logic for operations related to Asset entity.
//Performed CRUD Operation for asset entity.
@Service
public class AssetServiceImpl implements AssetService {

	@Autowired
	private AssetRepository assetRepo;
	
	Logger logger=LoggerFactory.getLogger(this.getClass().getSimpleName()); 

	//To Add New Asset
	@Override
	public Asset addAsset(Asset newAsset) throws AssetException {
		if(newAsset==null) throw new AssetException("Asset can't be empty");
		if(newAsset.getAssetType().trim().isEmpty() || newAsset.getAssetType()==null) throw new AssetException("Asset Type can't be Empty");
		logger.info("Asset Added. asset id:{}",newAsset.getAssetId());
		return assetRepo.save(newAsset);
	}

	// To Update The Existing Asset
	@Override
	public Asset updateAsset(Asset updateAsset) throws AssetException {
		// TODO Auto-generated method stub
		if(updateAsset==null) throw new AssetException("Asset can't be empty");
		if(updateAsset.getAssetType().trim().isEmpty() || updateAsset.getAssetType()==null) throw new AssetException("Asset Type can't be Empty");
		logger.info("Asset Updated. asset id:{}",updateAsset.getAssetId());
		return assetRepo.save(updateAsset);
	}

	//To Delete An Asset By Asset Type
	@Override
	public void deleteAssetByAssetType(String assetType) throws AssetException {
		// TODO Auto-generated method stub
		Asset assetDetails=assetRepo.getByAssetType(assetType);
		if(assetDetails==null) throw new AssetException("AssetType is not present");
		assetRepo.delete(assetDetails);
		logger.info("Asset Deleted. asset id:{}",assetDetails.getAssetId());
	}

	//To Delete An Asset By Asset Code
	@Override
	public void deleteAssetByAssetCode(int assetCode) throws AssetException {
		// TODO Auto-generated method stub
		Asset assetDetails=assetRepo.getByAssetCode(assetCode);
		if(assetDetails==null) throw new AssetException("AssetCode is not present");
		assetRepo.delete(assetDetails);
		logger.info("Asset Deleted. asset id:{}",assetDetails.getAssetId());
	}

	//To Get All the Assets
	@Override
	public List<AssetDto> getAssets(){
		// TODO Auto-generated method stub
		List<AssetDto> assetDtoList = new ArrayList<>();
		List<Asset> assetList = assetRepo.findAll();
		for (Asset asset:assetList){
			AssetDto assetDto = AssetDto.convertToAssetDto(asset);
			assetDtoList.add(assetDto);
		}
		logger.info("Fetched All Assets");
		return assetDtoList;
	}

	//To Get All The Assets Based On Sorted Order And Field
	//Field can be assetType or assetCode
	@Override
	public List<AssetDto> getSortedAssets(String Order,String field) throws AssetException {
		// TODO Auto-generated method stub
		if(field !="assetType" && field !="assetCode" ) throw new AssetException("doesn't match with the fields present");
		if(Order.equals(Constants.DESC)) {
			List<Asset> assetList = assetRepo.findAll(Sort.by(Sort.Direction.DESC,field));
			List<AssetDto> assetDtoList = new ArrayList<>();

			for (Asset asset:assetList){
				AssetDto assetDto = AssetDto.convertToAssetDto(asset);
				assetDtoList.add(assetDto);
			}
			logger.info("Return All Assets in Descending Order");
			return assetDtoList;
		}
		else {
			List<Asset> assetList = assetRepo.findAll(Sort.by(Sort.Direction.ASC,field));
			List<AssetDto> assetDtoList = new ArrayList<>();

			for (Asset asset:assetList){
				AssetDto assetDto = AssetDto.convertToAssetDto(asset);
				assetDtoList.add(assetDto);
			}
			logger.info("Return All Assets in Ascending Order");
			return assetDtoList;
		}
	}

	//To Get the Asset Details By Asset Type
	@Override
	public AssetDto getAssetByAssetType(String assetType) throws AssetException {
		// TODO Auto-generated method stub
	   	if (assetType == null || assetType.trim().isEmpty()) {
    		throw new AssetException("The Asset Type Can't be null or empty");
    	}
		 Asset assetDetails = assetRepo.getByAssetType(assetType);
		 AssetDto assetDto = AssetDto.convertToAssetDto(assetDetails);
		 if(assetDetails==null) throw new AssetException("AssetType is Invalid!!");
		 logger.info("Fetched Asset. asset id:{}",assetDto.getAssetId());
		 return assetDto;
	}

	//To Get The Asset Details By Asset Code
	@Override
	public AssetDto getAssetByAssetCode(int assetCode) throws AssetException {
		// TODO Auto-generated method stub
		if(String.valueOf(assetCode).length()!=3) throw new AssetException("Asset Code is wrong ");
		Asset assetDetails = assetRepo.getByAssetCode(assetCode);
		AssetDto assetDto = AssetDto.convertToAssetDto(assetDetails);
		logger.info("Fetched Asset. asset id:{}",assetDto.getAssetId());
		return assetDto;
	}
}