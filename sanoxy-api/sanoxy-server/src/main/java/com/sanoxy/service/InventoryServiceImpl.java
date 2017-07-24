
package com.sanoxy.service;

import com.sanoxy.dao.inventory.Inventory;
import com.sanoxy.dao.inventory.InventoryCategory;
import com.sanoxy.dao.user.Workspace;
import com.sanoxy.repository.inventory.InventoryCategoryRepository;
import com.sanoxy.repository.inventory.InventoryRepository;
import com.sanoxy.service.exception.ResourceMissingException;
import com.sanoxy.service.util.UserIdentity;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {
        
        @Autowired
        private InventoryCategoryRepository inventoryCategoryRepository;
        
        @Autowired
        private InventoryRepository inventoryRepository;
        
        @Autowired
        private UserService userService;
        
        @PersistenceContext
        private EntityManager entityManager;
        
        private Workspace getLoggedInWorkspace(UserIdentity identity) throws ResourceMissingException {
                Workspace workspace = userService.getIdentityInfo(identity).getWorkspace();
                if (workspace == null)
                        throw new ResourceMissingException("Workspace for the current login status does not exist.");
                return workspace;
        }

        @Override
        public Collection<InventoryCategory> getInventoryCategories(UserIdentity identity) throws ResourceMissingException {
                Workspace workspace = getLoggedInWorkspace(identity);
                return inventoryCategoryRepository.findByWorkspaceWidOrderByCategoryNameAsc(workspace.getWid());
        }

        @Override
        public boolean addInventoryCategory(UserIdentity identity, String categoryName) throws ResourceMissingException {
                Workspace workspace = getLoggedInWorkspace(identity);
                return null != inventoryCategoryRepository.save(new InventoryCategory(workspace, categoryName));
        }
        
        @Override
        public boolean deleteInventoryCategory(UserIdentity identity, Integer cid) {
                return 1 == inventoryCategoryRepository.deleteByCid(cid);
        }
        
        @Override
        public Collection<Inventory> getInventories(UserIdentity identity, Integer cid, Integer startIndex, Integer endIndex) {
                return inventoryRepository.findByCidWithPagination(cid, startIndex, endIndex - startIndex + 1);
        }

        @Transactional
        @Override
        public boolean addInventory(UserIdentity identity, Integer cid, Inventory inventory) throws ResourceMissingException {
                InventoryCategory category = entityManager.getReference(InventoryCategory.class, cid);
                if (category == null)
                        throw new ResourceMissingException("Category " + cid + " does not exist.");
                category.setNumInventories(category.getNumInventories() + 1);
                inventory.setInventoryCategory(category);
                inventoryRepository.save(inventory);
                return true;
        }

        @Transactional
        @Override
        public boolean deleteInventory(UserIdentity identity, Integer iid) {
                Inventory inventory = inventoryRepository.findByIid(iid);
                if (inventory == null)
                        return false;
                InventoryCategory category = inventory.getInventoryCategory();
                inventoryRepository.deleteByIid(iid);
                category.setNumInventories(category.getNumInventories() - 1);
                inventoryCategoryRepository.save(category);
                return true;
        }

        @Override
        public Collection<Inventory> search(UserIdentity identity, String keyword) {
                return inventoryRepository.searchInventoryByKeyword(keyword);
        }
}
