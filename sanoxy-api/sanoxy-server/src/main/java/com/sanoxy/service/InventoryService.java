
package com.sanoxy.service;

import com.sanoxy.dao.inventory.Inventory;
import com.sanoxy.dao.inventory.InventoryCategory;
import com.sanoxy.service.exception.ResourceMissingException;
import com.sanoxy.service.util.UserIdentity;
import java.util.Collection;


public interface InventoryService {
        
        public Collection<InventoryCategory> getInventoryCategories(UserIdentity identity) throws ResourceMissingException;
        public boolean addInventoryCategory(UserIdentity identity, String categoryName) throws ResourceMissingException;
        public boolean deleteInventoryCategory(UserIdentity identity, Integer cid) throws ResourceMissingException;
        public Collection<Inventory> getInventories(UserIdentity identity, Integer cid, Integer startIndex, Integer endIndex) throws ResourceMissingException;
        public boolean addInventory(UserIdentity identity, Integer cid, Inventory inventory) throws ResourceMissingException;
        public boolean deleteInventory(UserIdentity identity, Integer iid) throws ResourceMissingException;
        public Collection<Inventory> search(UserIdentity identity, String keyword) throws ResourceMissingException;
}
