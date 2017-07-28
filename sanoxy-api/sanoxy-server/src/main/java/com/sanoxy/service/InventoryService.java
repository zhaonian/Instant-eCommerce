
package com.sanoxy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sanoxy.dao.inventory.Inventory;
import com.sanoxy.dao.inventory.InventoryCategory;
import com.sanoxy.dao.inventory.InventoryImage;
import com.sanoxy.service.exception.ResourceMissingException;
import com.sanoxy.service.util.UserIdentity;
import java.util.Collection;


public interface InventoryService {
        
        public Collection<InventoryCategory> getInventoryCategories(UserIdentity identity) throws ResourceMissingException;
        public boolean addInventoryCategory(UserIdentity identity, String categoryName) throws ResourceMissingException;
        public boolean deleteInventoryCategory(UserIdentity identity, Integer cid) throws ResourceMissingException;
        public Collection<Inventory> getInventories(UserIdentity identity, Integer cid, Integer startIndex, Integer endIndex) throws ResourceMissingException;
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
                                      Collection<byte[]> imageFiles) throws ResourceMissingException, JsonProcessingException;
        public boolean deleteInventory(UserIdentity identity, Integer iid) throws ResourceMissingException;
        public Collection<Inventory> searchWorkspaceInventories(UserIdentity identity, String keyword) throws ResourceMissingException;
        public Collection<InventoryImage> getInventoryImages(UserIdentity identity, Integer iid) throws ResourceMissingException;
}
