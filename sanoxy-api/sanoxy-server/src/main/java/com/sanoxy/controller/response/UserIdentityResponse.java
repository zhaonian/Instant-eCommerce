package com.sanoxy.controller.response;

import com.sanoxy.controller.service.UserIdentity;

public class UserIdentityResponse extends UserIdentity {
	
        public UserIdentityResponse() {
        }
        
	public UserIdentityResponse(Integer dbid) {
		super(dbid);
	}
}
