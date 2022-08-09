package com.batonsystems.banksimulator.service;

import java.util.List;

import com.batonsystems.banksimulator.dto.AssetDto;
import com.batonsystems.banksimulator.entity.Asset;
import com.batonsystems.banksimulator.exception.AssetException;

public interface AssetService {

	public Asset addAsset(Asset newAsset) throws AssetException;

	public Asset updateAsset(Asset updateAsset) throws AssetException;

	public List<AssetDto> getAssets();

	public List<AssetDto> getSortedAssets(String Order,String field) throws AssetException;

	public AssetDto getAssetByAssetType(String assetType) throws AssetException;

	public AssetDto getAssetByAssetCode(int assetCode) throws AssetException;

	public void deleteAssetByAssetType(String assetType) throws AssetException;

	public void deleteAssetByAssetCode(int assetCode) throws AssetException;
}
