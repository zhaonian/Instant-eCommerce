
package com.sanoxy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanoxy.dao.inventory.Inventory;
import com.sanoxy.dao.inventory.InventoryCategory;
import com.sanoxy.dao.inventory.InventoryImage;
import com.sanoxy.dao.user.Workspace;
import com.sanoxy.repository.inventory.InventoryCategoryRepository;
import com.sanoxy.repository.inventory.InventoryRepository;
import com.sanoxy.service.exception.ResourceMissingException;
import com.sanoxy.service.util.UserIdentity;
import java.util.ArrayList;
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
        
        private void checkInventoryCategoryOwnership(UserIdentity identity, Integer cid) throws ResourceMissingException {
                Workspace workspace = getLoggedInWorkspace(identity);
                if (!inventoryCategoryRepository.existsByWorkspaceWidAndCid(workspace.getWid(), cid))
                        throw new ResourceMissingException("You don't own the inventory category with cid <" + cid + ">");
        }
        
        private void checkInventoryOwnership(UserIdentity identity, Integer iid) throws ResourceMissingException {
                Workspace workspace = getLoggedInWorkspace(identity);
                if (!inventoryRepository.existsByInventoryCategoryWorkspaceWidAndIid(workspace.getWid(), iid))
                        throw new ResourceMissingException("You don't own the inventory with iid <" + iid + ">");
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
        public boolean deleteInventoryCategory(UserIdentity identity, Integer cid) throws ResourceMissingException {
                checkInventoryCategoryOwnership(identity, cid);
                return 1 == inventoryCategoryRepository.deleteByCid(cid);
        }
        
        @Override
        public Collection<Inventory> getInventories(UserIdentity identity, Integer cid, Integer startIndex, Integer endIndex) 
                                                                                                    throws ResourceMissingException {
                checkInventoryCategoryOwnership(identity, cid);
                Collection<Inventory> inventories = inventoryRepository.findByCidWithPagination(cid, startIndex, endIndex - startIndex + 1);
                inventories.forEach(inventory -> inventory.setInventoryImages(null));
                return inventories;
        }
        
        @Override
        public Collection<InventoryImage> getInventoryImages(UserIdentity identity, Integer iid) throws ResourceMissingException {
                Inventory inventory = inventoryRepository.findByIid(iid);
                if (inventory == null)
                        throw new ResourceMissingException("Inventory with iid <" + iid + "> doesn't exist.");
                checkInventoryOwnership(identity, iid);
                return inventory.getInventoryImages();
        }

        @Transactional
        @Override
        public Inventory addInventory(UserIdentity identity, Integer cid, 
                                      Float suggestPrice, 
                                      String ean, 
                                      String title, 
                                      String brand, 
                                      String description,
                                      String amazonItemType,
                                      String amazonProductType,
                                      Collection<String> bullets,
                                      String keyword,
                                      Collection<byte[]> imageFiles) throws ResourceMissingException, 
                                                                            JsonProcessingException {
                InventoryCategory category = entityManager.getReference(InventoryCategory.class, cid);
                if (category == null)
                        throw new ResourceMissingException("Category " + cid + " does not exist.");
                
                checkInventoryCategoryOwnership(identity, cid);
                
                category.incNumInventories();
                
                Collection<InventoryImage> images = new ArrayList();
                imageFiles.forEach((file) -> {
                        images.add(new InventoryImage("", file));
                });
                
                return inventoryRepository.save(new Inventory(category, 
                                                       suggestPrice, 
                                                       ean, 
                                                       title, 
                                                       brand, 
                                                       description, 
                                                       amazonItemType, amazonProductType, 
                                                       mapper.writeValueAsString(bullets), 
                                                       keyword, 
                                                       images));
        }

        @Transactional
        @Override
        public boolean deleteInventory(UserIdentity identity, Integer iid) throws ResourceMissingException {
                Inventory inventory = inventoryRepository.findByIid(iid);
                if (inventory == null)
                        throw new ResourceMissingException("Inventory with iid <" + iid + "> does not exist.");
                
                checkInventoryOwnership(identity, iid);
                
                inventoryRepository.deleteByIid(iid);
                
                InventoryCategory category = inventory.getInventoryCategory();
                category.decNumInventories();
                inventoryCategoryRepository.save(category);
                return true;
        }

        @Override
        public Collection<Inventory> searchWorkspaceInventories(UserIdentity identity, String keyword) throws ResourceMissingException {
                Workspace workspace = getLoggedInWorkspace(identity);
                return inventoryRepository.searchWorkspaceInventoriesByKeyword(workspace.getWid(), keyword);
        }
}
