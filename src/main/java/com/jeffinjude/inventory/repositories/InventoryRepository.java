package com.jeffinjude.inventory.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jeffinjude.inventory.entities.InventoryDetails;


public interface InventoryRepository extends JpaRepository<InventoryDetails, Integer> {
	
	@Modifying
	@Query("update InventoryDetails id set id.productQuantity = :quantity where id.productId = :productId")
	int updateInventoryQuantity(@Param("quantity") double quantity, @Param("productId") int productId);

	Optional<InventoryDetails> findByProductId(int productId);
}
