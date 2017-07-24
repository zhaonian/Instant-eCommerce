
package com.sanoxy.service;

import com.sanoxy.dao.inventory.Inventory;
import com.sanoxy.dao.inventory.InventoryCategory;
import com.sanoxy.repository.inventory.InventoryCategoryRepository;
import com.sanoxy.repository.inventory.InventoryRepository;
import com.sanoxy.service.exception.ResourceMissingException;
import com.sanoxy.service.util.UserIdentity;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;


public class InventoryServiceImpl implements InventoryService {
        
        @Autowired
        private InventoryCategoryRepository inventoryCategoryRepository;
        
        @Autowired
        private InventoryRepository inventoryRepository;
        
        @PersistenceContext
        private EntityManager entityManager;

        @Override
        public Collection<InventoryCategory> getInventoryCategories(UserIdentity identity) {
                return inventoryCategoryRepository.findAllByOrderByCategoryNameAsc();
        }

        @Override
        public boolean addInventoryCategory(UserIdentity identity, InventoryCategory category) {
                return null != inventoryCategoryRepository.save(category);
        }
        
        @Override
        public boolean deleteInventoryCategory(UserIdentity identity, Integer cid) {
                return 1 == inventoryCategoryRepository.deleteByCid(cid);
        }
        
        @Override
        public Collection<Inventory> getInventories(UserIdentity identity, Integer cid, Integer startIndex, Integer endIndex) {
                return inventoryRepository.findByCidWithPagination(cid, startIndex, endIndex - startIndex + 1);
        }

        @Override
        public boolean addInventory(UserIdentity identity, Integer cid, Inventory inventory) throws ResourceMissingException {
                InventoryCategory category = entityManager.getReference(InventoryCategory.class, cid);
                if (category == null)
                        throw new ResourceMissingException("Category " + cid + " does not exist.");
                inventory.setInventoryCategory(category);
                inventoryRepository.save(inventory);
                return true;
        }

        @Override
        public boolean deleteInventory(UserIdentity identity, Integer iid) {
                return 1 == inventoryRepository.deleteByIid(iid);
        }

        @Override
        public Collection<Inventory> search(UserIdentity identity, String keyword) {
                return inventoryRepository.searchInventoryByKeyword(keyword);
        }
}
