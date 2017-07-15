
package com.sanoxy.service.util;

/**
 * @author davis
 */
public enum UserPermission {
        AddUserToWorkspace("Workspace", 1),
        RemoveUserFromWorkspace("Workspace", 2),
        ChangePermissions("Attribute", 3);

        
        private final String category;
        private final Integer ordinal;
        
        UserPermission(String category, Integer ordinal) {
                this.category = category;
                this.ordinal = ordinal;
        }
        
        public Permission getPermission() {
                return new Permission("User permission", category, ordinal);
        }
}
