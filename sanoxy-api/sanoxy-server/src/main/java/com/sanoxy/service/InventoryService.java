
package com.sanoxy.service;

import com.sanoxy.dao.inventory.InventoryCategory;
import com.sanoxy.service.util.UserIdentity;
import java.util.List;


public interface InventoryService {
        
        List<InventoryCategory> getInventoryCategories(UserIdentity identity);
}
