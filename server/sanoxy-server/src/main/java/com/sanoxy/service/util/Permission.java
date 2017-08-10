
package com.sanoxy.service.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;


public class Permission {
        
        private String permissionCategory;
        private String permissionSubCategory;
        private Integer permissionType;
        private String actionDetail;
        
        public Permission() {
        }
         
        public Permission(String permissionCategory, String permissionSubCategory, Integer permissionType, String actionDetail) {
                this.permissionCategory = permissionCategory;
                this.permissionSubCategory = permissionSubCategory;
                this.permissionType = permissionType;
                this.actionDetail = actionDetail;
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
        
        public String getActionDetail() {
                return this.actionDetail;
        }
        
        public void setActionDetail(String actionDetail) {
                this.actionDetail = actionDetail;
        }
        
        @JsonIgnore
        @Override
        public boolean equals(Object o) {
                if (!(o instanceof Permission))
                        return false;
                Permission rhs = (Permission) o;
                return permissionType.equals(rhs.permissionType);
        }

        @Override
        public int hashCode() {
                int hash = 3;
                hash = 43 * hash + Objects.hashCode(this.permissionType);
                return hash;
        }
}
