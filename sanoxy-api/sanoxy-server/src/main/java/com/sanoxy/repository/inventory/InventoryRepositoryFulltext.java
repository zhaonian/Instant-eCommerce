
package com.sanoxy.repository.inventory;

import com.sanoxy.dao.inventory.Inventory;
import java.util.Collection;


public interface InventoryRepositoryFulltext {
        public Collection<Inventory> searchInventoryByKeyword(String keyword);
}
