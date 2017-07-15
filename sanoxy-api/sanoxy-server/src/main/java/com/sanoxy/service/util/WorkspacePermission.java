package com.sanoxy.service.util;

/**
 * @author davis
 */
public enum WorkspacePermission {
        ReadInventory("Inventory", 1),
        DeleteInventory("Inventory", 2),
        EditInventory("Inventory", 3),
        CreateInventory("Inventory", 4),
        
        ReadCategory("Category", 10),
        DeleteCategory("Category", 11),
        EditCategory("Category", 12),
        CreateCategory("Category", 13);
        
        private final String category;
        private final Integer ordinal;
        
        WorkspacePermission(String category, Integer ordinal) {
                this.category = category;
                this.ordinal = ordinal;
        }
        
        public Permission getPermission() {
                return new Permission("Workspace permission", category, ordinal*1000);
        }
}
