
package com.sanoxy.repository.inventory;

import com.sanoxy.dao.inventory.Inventory;
import java.util.List;


public interface InventoryRepositoryFulltext {
        public List<Inventory> searchInventoryByKeyword(String keyword);
}
