
package com.sanoxy.service;

import com.sanoxy.dao.inventory.Inventory;
import com.sanoxy.dao.inventory.InventoryCategory;
import com.sanoxy.service.exception.ResourceMissingException;
import com.sanoxy.service.util.UserIdentity;
import java.util.Collection;


public interface InventoryService {
        
        public Collection<InventoryCategory> getInventoryCategories(UserIdentity identity);
        public boolean addInventoryCategory(UserIdentity identity, InventoryCategory category);
        public boolean deleteInventoryCategory(UserIdentity identity, Integer cid);
        public Collection<Inventory> getInventories(UserIdentity identity, Integer cid, Integer startIndex, Integer endIndex);
        public boolean addInventory(UserIdentity identity, Integer cid, Inventory inventory) throws ResourceMissingException;
        public boolean deleteInventory(UserIdentity identity, Integer iid);
        public Collection<Inventory> search(UserIdentity identity, String keyword);
}
