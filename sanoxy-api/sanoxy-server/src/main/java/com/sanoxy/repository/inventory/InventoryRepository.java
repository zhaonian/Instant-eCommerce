
package com.sanoxy.repository.inventory;

import com.sanoxy.dao.inventory.Inventory;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author luan
 */
public interface InventoryRepository extends CrudRepository<Inventory, Integer>, InventoryRepositoryFulltext {
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
