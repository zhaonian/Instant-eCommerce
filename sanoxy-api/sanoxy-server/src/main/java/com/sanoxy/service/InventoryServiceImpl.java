
package com.sanoxy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        
        private final ObjectMapper mapper = new ObjectMapper();
        
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
        public boolean addInventory(UserIdentity identity, Integer cid, 
                                    Float suggestPrice, 
                                    String ean, 
                                    String title, 
                                    String brand, 
                                    String description,
                                    String amazonItemType,
                                    String amazonProductType,
                                    Collection<String> bullets,
                                    String keyword,
                                    Collection<String> imageUrls) throws ResourceMissingException, JsonProcessingException {
                InventoryCategory category = entityManager.getReference(InventoryCategory.class, cid);
                if (category == null)
                        throw new ResourceMissingException("Category " + cid + " does not exist.");
                category.incNumInventories();
                inventoryRepository.save(new Inventory(category, 
                                                       suggestPrice, 
                                                       ean, 
                                                       title, 
                                                       brand, 
                                                       description, 
                                                       amazonItemType, amazonProductType, 
                                                       mapper.writeValueAsString(bullets), 
                                                       keyword, 
                                                       mapper.writeValueAsString(imageUrls)));
                return true;
        }

        @Transactional
        @Override
        public boolean deleteInventory(UserIdentity identity, Integer iid) throws ResourceMissingException {
                Inventory inventory = inventoryRepository.findByIid(iid);
                if (inventory == null)
                        throw new ResourceMissingException("Inventory with iid <" + iid + "> does not exist.");
                inventoryRepository.deleteByIid(iid);
                
                InventoryCategory category = inventory.getInventoryCategory();
                category.decNumInventories();
                inventoryCategoryRepository.save(category);
                return true;
        }

        @Override
        public Collection<Inventory> search(UserIdentity identity, String keyword) {
                return inventoryRepository.searchInventoryByKeyword(keyword);
        }
}
