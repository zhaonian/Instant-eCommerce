
package com.sanoxy.service.util;


public enum UserPermission {
        AddUserToWorkspace("Workspace", "add user to workspace", 1),
        RemoveUserFromWorkspace("Workspace", "remove user from workspace", 2),
        ChangeUserPermissions("Administration", "change user permission", 3),
        CreateAdministrator("Administration", "create administrator", 4),
        CreatePermissionRule("Administration", "create permission rule", 5);

        
        private final String category;
        private final String action;
        private final Integer ordinal;
        
        private static final String PERMISSION_CATEGORY = "User permission";
        
        UserPermission(String category, String action, Integer ordinal) {
                this.category = category;
                this.ordinal = ordinal;
                this.action = action;
        }
        
        public Permission getPermission() {
                return new Permission(PERMISSION_CATEGORY, category, ordinal, action);
        }
        
        public static boolean isUserPermission(Permission perm) {
                return perm.getPermissionCategory().equals(PERMISSION_CATEGORY);
        }
}
