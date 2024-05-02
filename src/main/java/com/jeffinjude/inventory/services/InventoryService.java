package com.jeffinjude.inventory.services;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.jeffinjude.inventory.entities.InventoryDetails;
import com.jeffinjude.inventory.entities.Product;
import com.jeffinjude.inventory.repositories.InventoryRepository;


@Service
public class InventoryService {
	
	private final WebClient webClient;

	
	public InventoryService(WebClient webClient) {
		this.webClient = webClient;
	}
	
	private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	public InventoryDetails addInventoryDetailsService(InventoryDetails inventoryDetails) throws Exception {

		Optional<Product> fetchedProduct = Optional.ofNullable(webClient.get().uri("/ecomm-product-catalogue/api/v1/product/details/"+inventoryDetails.getProductId()).retrieve()
	      .bodyToMono(Product.class).block());
		log.info("Inside addInventoryService. fetchedProduct: " + fetchedProduct.toString());
		
		Optional<InventoryDetails> fetchedInventoryDetails = getInventoryDetailsService(inventoryDetails.getProductId());
		log.info("Inside addInventoryService. fetchedInventoryDetails: " + fetchedProduct.toString());
		
		if(fetchedProduct.isPresent() && fetchedInventoryDetails.isEmpty()) {
			InventoryDetails createdInventory = inventoryRepository.save(inventoryDetails);
			createdInventory.setMessage("Inventory Created.");
			return createdInventory;
		}
		else {
			throw new Exception("Invalid Product id / Inventory already created.");
		}
	}
	
	public Optional<InventoryDetails> getInventoryDetailsService(int productId) {
		Optional<InventoryDetails> inventoryDetails = inventoryRepository.findByProductId(productId);
		return inventoryDetails;
	}
	
	@Transactional
	public void updatInventoryQuantityService(InventoryDetails inventoryDetails, String operation) throws Exception {
		Optional<Product> fetchedProduct = Optional.ofNullable(webClient.get().uri("/ecomm-product-catalogue/api/v1/product/details/"+inventoryDetails.getProductId()).retrieve()
			      .bodyToMono(Product.class).block());
		log.info("Inside updatInventoryQuantityService. fetchedProduct: " + fetchedProduct.toString());
		
		Optional<InventoryDetails> fetchedInventoryDetails = getInventoryDetailsService(inventoryDetails.getProductId());
		log.info("Inside updatInventoryQuantityService. fetchedInventoryDetails: " + fetchedInventoryDetails.toString());
		
		if(fetchedProduct.isPresent() && fetchedInventoryDetails.isPresent()) {
			double existingQuantity = fetchedInventoryDetails.get().getProductQuantity();
			double updatedQuantity = existingQuantity;
			if(operation.equalsIgnoreCase("increase")) {
				updatedQuantity = existingQuantity + inventoryDetails.getProductQuantity();
			}
			else if(operation.equalsIgnoreCase("decrease")) {
				updatedQuantity = existingQuantity - inventoryDetails.getProductQuantity();
			}
			
			if(updatedQuantity >= 0) {
				inventoryRepository.updateInventoryQuantity(updatedQuantity, inventoryDetails.getProductId());
			}
			else {
				throw new Exception("Invalid quantity: " + updatedQuantity);
			}
		}
		else {
			throw new Exception("Invalid product id / Inventory not present.");
		}
	}
	
}
