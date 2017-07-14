/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanoxy.repository.inventory;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sanoxy.dao.inventory.Inventory;

/**
 *
 * @author luan
 */
public interface InventoryRepository extends CrudRepository<Inventory, Integer>{
	@Query(value="SELECT * "
			+ "FROM inventory AS I, inventory_category AS IC "
			+ "WHERE IC.cid = I.cid AND IC.cid = (:categoryId) "
			+ "ORDER BY I.brand "
			+ "OFFSET (:startIndex) ROWS " 
			+ "FETCH NEXT (:numRowsToShow) ROWS ONLY", nativeQuery=true)
	public List<Inventory> findAllInventoryItemsByCategoryId(@Param("categoryId") Integer categoryId, 
								 @Param("startIndex") Integer startIndex, 
								 @Param("numRowsToShow") Integer numRowsToShow);
}
