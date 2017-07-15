
package com.sanoxy.service.util;

/**
 * @author davis
 */
public class Permission {
        
        private String permissionCategory;
        private String permissionSubCategory;
        private Integer permissionType;
        
        public Permission(String permissionCategory, String permissionSubCategory, Integer permissionType) {
                this.permissionCategory = permissionCategory;
                this.permissionSubCategory = permissionSubCategory;
                this.permissionType = permissionType;
        }
        
        public String getPermissionCategory() {
                return this.permissionCategory;
        }
        
        public void setPermissionCategory(String permissionCategory) {
                this.permissionCategory = permissionCategory;
        }
        
        public String getPermissionSubCategory() {
                return this.permissionSubCategory;
        }
        
        public void setPermissionSubCategory(String permissionSubCategory) {
                this.permissionSubCategory = permissionSubCategory;
        }
        
        public Integer getPermissionType() {
                return this.permissionType;
        }
        
        public void setPermissionType(Integer permissionType) {
                this.permissionType = permissionType;
        }
}
