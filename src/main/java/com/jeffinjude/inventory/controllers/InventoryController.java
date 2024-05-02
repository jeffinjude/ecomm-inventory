package com.jeffinjude.inventory.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeffinjude.inventory.entities.InventoryDetails;
import com.jeffinjude.inventory.services.InventoryService;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {
	
	@Value("${ecomm.inventory.teststatus}")
	private String testProp;
	
	@Autowired
	private InventoryService inventoryService;
	
	private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
	
	@GetMapping("test")
	public ResponseEntity<String> test() {	
		return ResponseEntity.ok("Inventory Test status: " + testProp);
	}
	
	@PostMapping("add")
	public ResponseEntity<InventoryDetails> addInventoryDetails(@RequestBody InventoryDetails inventoryDetails) {
		ResponseEntity<InventoryDetails> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			InventoryDetails createdInventoryDetails = inventoryService.addInventoryDetailsService(inventoryDetails);
			log.info("Inside addInventoryDetails. Created inventory: " + createdInventoryDetails.toString());
			responseEntity = new ResponseEntity<>(createdInventoryDetails, HttpStatus.OK);
		}
		catch(Exception e) {
			log.debug("Inside addInventoryDetails. Exception: " + e.getMessage());
			InventoryDetails inventoryDetailsErr = new InventoryDetails();
			inventoryDetailsErr.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(inventoryDetailsErr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@GetMapping("details/{productId}")
	public ResponseEntity<InventoryDetails> getInventoryDetails(@PathVariable("productId") int productId) {
		ResponseEntity<InventoryDetails> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			Optional<InventoryDetails> fetchedInventoryDetails = inventoryService.getInventoryDetailsService(productId);
			log.info("Inside getInventoryDetails. Fetched inventory details: " + fetchedInventoryDetails.toString());
			if(fetchedInventoryDetails.isPresent()) {
				responseEntity = new ResponseEntity<>(fetchedInventoryDetails.get(), HttpStatus.OK);
			}
			else {
				responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
		}
		catch(Exception e) {
			log.debug("Inside getInventoryDetails. Exception: " + e.getMessage());
		}
		return responseEntity;
	}
	
	@PostMapping("update/increase")
	public ResponseEntity<String> increaseProductInventory(@RequestBody InventoryDetails inventoryDetails) {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			inventoryService.updatInventoryQuantityService(inventoryDetails, "increase");
			responseEntity = new ResponseEntity<>("Product quantity increased by " + inventoryDetails.getProductQuantity(), HttpStatus.OK);
		}
		catch(Exception e) {
			log.debug("Inside increaseProductInventory. Exception: " + e.getMessage());
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@PostMapping("update/decrease")
	public ResponseEntity<String> decreaseProductInventory(@RequestBody InventoryDetails inventoryDetails) {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			inventoryService.updatInventoryQuantityService(inventoryDetails, "decrease");
			responseEntity = new ResponseEntity<>("Product quantity decreased by " + inventoryDetails.getProductQuantity(), HttpStatus.OK);
		}
		catch(Exception e) {
			log.debug("Inside increaseProductInventory. Exception: " + e.getMessage());
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}
