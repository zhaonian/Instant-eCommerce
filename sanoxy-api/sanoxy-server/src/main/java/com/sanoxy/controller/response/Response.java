package com.sanoxy.controller.response;

public class Response {
	
	/**
	 * List of different statuses.
	 * Currently, we only have two statuses
	 * 1 for success, and 0 for failed.
	 */
	public enum Status {
		Success(1), Failed(0);
		private final int intValue;
		
		private Status(int intValue) {
			this.intValue = intValue;
		}
		public int intValue() {
			return intValue;
		}
	}
	
	protected int status = 0;
	
	public Response() {} 
	
	public Response(Status status) {
		this.status = status.intValue;
	}
	public int getStatus() {
		return status;
	}
}

