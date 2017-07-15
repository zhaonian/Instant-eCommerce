package com.sanoxy.controller.response;

import com.sanoxy.service.util.UserIdentity;

public class UserIdentityResponse extends Response {
        
        private UserIdentity userIdentity;
	
        public UserIdentityResponse() {
        }
        
	public UserIdentityResponse(UserIdentity userIdentity) {
		this.userIdentity = userIdentity;
	}
        
        public UserIdentity getUserIdentity() {
                return userIdentity;
        }
        
        public void setUserIdentity(UserIdentity userIdentity) {
                this.userIdentity = userIdentity;
        }
}
