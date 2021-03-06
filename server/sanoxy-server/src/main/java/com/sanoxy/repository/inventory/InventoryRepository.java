
package com.sanoxy.repository.inventory;

import com.sanoxy.dao.inventory.Inventory;
import java.util.Collection;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface InventoryRepository extends CrudRepository<Inventory, Integer>, InventoryRepositoryFulltext {
	@Query(value="SELECT * "
			+ "FROM inventory AS I, inventory_category AS IC "
			+ "WHERE IC.cid = I.cid AND IC.cid = (:categoryId) "
			+ "ORDER BY I.brand "
			+ "OFFSET (:startIndex) ROWS " 
			+ "FETCH NEXT (:numRowsToShow) ROWS ONLY", nativeQuery=true)
	public Collection<Inventory> findByCidWithPagination(@Param("categoryId") Integer categoryId, 
							     @Param("startIndex") Integer startIndex, 
							     @Param("numRowsToShow") Integer numRowsToShow);
        @Transactional
        public Inventory findByIid(Integer iid);
/*        
        @Transactional
        public Collection<InventoryImage> findInventoryImagesByIid(Integer iid);
*/
        public Long deleteByIid(Integer iid);

        public boolean existsByInventoryCategoryWorkspaceWidAndIid(Integer wid, Integer iid);
}
