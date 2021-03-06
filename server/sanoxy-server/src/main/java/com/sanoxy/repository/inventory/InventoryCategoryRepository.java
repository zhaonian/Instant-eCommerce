package com.sanoxy.repository.inventory;

import com.sanoxy.dao.inventory.InventoryCategory;
import java.util.Collection;
import org.springframework.data.repository.CrudRepository;

public interface InventoryCategoryRepository extends CrudRepository<InventoryCategory, Integer> {

	public Collection<InventoryCategory> findByWorkspaceWidOrderByCategoryNameAsc(Integer wid);
        public Collection<InventoryCategory> findByWorkspaceWidAndCategoryName(Integer wid, String categoryName);
	public InventoryCategory findByCid(Integer cid);
        public Long deleteByCid(Integer cid);
        public boolean existsByWorkspaceWidAndCid(Integer wid, Integer cid);
}
