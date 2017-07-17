
package com.sanoxy.service.exception;

import com.sanoxy.service.util.Permission;


public class PermissionDeniedException extends Exception {
        
        private static final long serialVersionUID = 1L;
	
	private final String message = "Permission denied!";
        private final Permission permission;
	
	public PermissionDeniedException(Permission permission) {
                this.permission = permission;
        }
	
	@Override
	public String getMessage() {
		return message + " Cause: you don't have the permission under the category \"" 
                        + permission.getPermissionCategory() + " & " + permission.getPermissionSubCategory() + "\""
                        + " over permission code <" + permission.getPermissionType() + ">"
                        + " to perform the action \"" + permission.getActionDetail() +"\"";
	}
}
