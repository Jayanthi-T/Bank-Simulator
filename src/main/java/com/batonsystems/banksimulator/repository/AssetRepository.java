package com.batonsystems.banksimulator.repository;

import com.batonsystems.banksimulator.entity.Asset;

import org.springframework.data.jpa.repository.JpaRepository;

//This AssetRepository class holds the database operations related to Asset entity.
public interface AssetRepository extends JpaRepository<Asset,Integer> {
	public Asset getByAssetType(String deletedAssetType);
	public Asset getByAssetCode(int assetCode);
	public boolean existsByAssetCode(int assetCode);
	public boolean existsByAssetType(String assetType);
}

