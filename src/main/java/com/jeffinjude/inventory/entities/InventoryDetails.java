package com.jeffinjude.inventory.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "inventory_details")
public class InventoryDetails {
	
	@Id
    @Column(name = "inventory_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int inventoryId;
	
	@Column(name = "product_id", nullable = false)
	private int productId;
	
	@Column(name = "product_quantity", nullable = false)
	private double productQuantity;
	
	@Transient
	private String message;
	
	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public double getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(double productQuantity) {
		this.productQuantity = productQuantity;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "InventoryDetails [inventoryId=" + inventoryId + ", productId=" + productId + ", productQuantity="
				+ productQuantity + "]";
	}
	
}
