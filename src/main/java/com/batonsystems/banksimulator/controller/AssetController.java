package com.batonsystems.banksimulator.controller;

import java.util.List;

import com.batonsystems.banksimulator.dto.AssetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.batonsystems.banksimulator.constants.Constants;
import com.batonsystems.banksimulator.entity.Asset;
import com.batonsystems.banksimulator.exception.AssetException;
import com.batonsystems.banksimulator.service.AssetService;

//This AssetController class is responsible to create the endpoints and to send and receive request related to Asset entity.
@RestController
public class AssetController {
	
	@Autowired
	private AssetService assetService;
	
	
	//To Get All The Assets
	@GetMapping("/getAssets")
	public List<AssetDto> getAssets() {
			return assetService.getAssets();
	}
	
	//To Get Asset Details of a particular Asset Type 
	@GetMapping("/getAssetByAssetType/{assetType}")
	public ResponseEntity<?> getAssetByAssetType(@PathVariable String assetType) {
		try {
			return ResponseEntity.ok(assetService.getAssetByAssetType(assetType));
		}
		catch(AssetException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());			
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
	
	//To Get Asset Details of a particular Asset Code 
	@GetMapping("/getAssetByAssetCode/{assetCode}")
	public ResponseEntity<?> getAssetByAssetCode(@PathVariable int assetCode) {
		try {
			return ResponseEntity.ok(assetService.getAssetByAssetCode(assetCode)); 
		}
		catch(AssetException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());			
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
	
	//To Get All The Assets Based On Sorted Order And Field
	//Field can be assetType or assetCode
	@GetMapping("/getSortedAssets/{Order}/{field}")
	public ResponseEntity<?> getSortedAssets(@PathVariable String Order,@PathVariable String field) {
		try {
			return ResponseEntity.ok(assetService.getSortedAssets(Order,field));
		}
		catch(AssetException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());			
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
	
	//To Add An Asset
	@PostMapping("/addAsset")
	public ResponseEntity<?> addAsset(@RequestBody Asset newAsset) {
		try {
			assetService.addAsset(newAsset);
			return ResponseEntity.ok(Constants.ASSET_ADDED_SUCCESS_RESPONSE);
		}
		catch(AssetException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());			
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
	
	//To Update An Existing Asset
	@PutMapping("/updateAsset")
	public ResponseEntity<?> updateAsset(@RequestBody Asset updateAsset) {
		try {
			assetService.updateAsset(updateAsset);
			return ResponseEntity.ok(Constants.ASSET_UPDATED_SUCCESS_RESPONSE);
		}
		catch(AssetException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());			
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
	
	//To Delete An Asset Based On Asset Type
	@DeleteMapping("/deleteAssetByAssetType/{assetType}")
	public ResponseEntity<?> deleteAssetByAssetType(@PathVariable String assetType) {
		try {
			assetService.deleteAssetByAssetType(assetType);
			return ResponseEntity.ok(Constants.ASSET_DELETED_SUCCESS_RESPONSE);
		}
		catch(AssetException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());			
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
	
	//To Delete An Asset Based On Asset Code
	@DeleteMapping("/deleteAssetByAssetCode/{assetCode}")
	public ResponseEntity<?> deleteAssetByAssetCode(@PathVariable int assetCode) {
		try {
			assetService.deleteAssetByAssetCode(assetCode);
			return ResponseEntity.ok(Constants.ASSET_DELETED_SUCCESS_RESPONSE);
		}
		catch(AssetException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());			
		}
		catch(Exception exception) {
			return ResponseEntity.internalServerError().body("Something Went Wrong");
		}
	}
}
