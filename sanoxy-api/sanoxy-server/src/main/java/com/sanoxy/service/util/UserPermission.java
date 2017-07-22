
package com.sanoxy.service.util;


public enum UserPermission {
        AddUserToWorkspace("Workspace", "add user to workspace", 1),
        RemoveUserFromWorkspace("Workspace", "remove user from workspace", 2),
        CreateWorkspace("Workspace", "Create workspace", 3),
        DeleteWorkspace("Workspace", "Delete workspace", 4),
        AccessWorkspace("Workspace", "Access workspace", 5),
        ChangeUserPermissions("Administration", "change user permission", 6),
        CreateAdministrator("Administration", "create administrator", 7),
        CreatePermissionRule("Administration", "create permission rule", 8);

        
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
