package com.sanoxy.repository.inventory;

import com.sanoxy.dao.inventory.InventoryCategory;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface InventoryCategoryRepository extends CrudRepository<InventoryCategory, Integer> {

	public List<InventoryCategory> findAllByOrderByCategoryNameAsc();
	
        public List<InventoryCategory> findByCategoryName(String categoryName);
                
	public InventoryCategory findByCid(Integer cid);

        public Long deleteByCid(Integer cid);
}
