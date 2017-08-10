
package com.sanoxy.service.exception;


public class DuplicatedWorkspaceException extends Exception {
        
        private static final long serialVersionUID = 1L;
	
	private final String message = "Workspace already exists";
	
	public DuplicatedWorkspaceException() { }
	
	@Override
	public String getMessage() {
		return message;
	}
}
