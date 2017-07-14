package com.sanoxy.repository.inventory;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sanoxy.dao.inventory.InventoryCategory;

public interface InventoryCategoryRepository extends CrudRepository<InventoryCategory, Integer> {
	@Query("SELECT category FROM InventoryCategory")
	public List<String> findAllCategoryNames();
	
	public InventoryCategory findByCid(Integer cid);
}
