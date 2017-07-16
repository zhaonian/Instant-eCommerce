package com.sanoxy.service.util;

/**
 * @author davis
 */
public enum WorkspacePermission {
        ReadInventory("Inventory", "access inventory", 1),
        DeleteInventory("Inventory", "delete inventory", 2),
        EditInventory("Inventory", "edit inventory", 3),
        CreateInventory("Inventory", "create inventory", 4),
        
        ReadCategory("Category", "access inventory category", 10),
        DeleteCategory("Category", "delete inventory category", 11),
        EditCategory("Category", "edit inventory category", 12),
        CreateCategory("Category", "create inventory category", 13);
        
        private final String category;
        private final String action;
        private final Integer ordinal;
        
        private static final String PERMISSION_CATEGORY = "Workspace permission";
        
        WorkspacePermission(String category, String action, Integer ordinal) {
                this.category = category;
                this.action = action;
                this.ordinal = ordinal;
        }
        
        public Permission getPermission() {
                return new Permission(PERMISSION_CATEGORY, category, ordinal*1000, action);
        }
        
        public static boolean isWorkspacePermission(Permission perm) {
                return perm.getPermissionCategory().equals(PERMISSION_CATEGORY);
        }
}
