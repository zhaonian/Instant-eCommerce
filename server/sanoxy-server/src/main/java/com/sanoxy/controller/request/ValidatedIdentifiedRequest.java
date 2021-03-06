
package com.sanoxy.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sanoxy.service.exception.InvalidRequestException;
import com.sanoxy.service.util.UserIdentity;


public class ValidatedIdentifiedRequest implements ValidatedRequest {
        
        private UserIdentity userIdentity;
        
        public ValidatedIdentifiedRequest() {
        }
        
        public ValidatedIdentifiedRequest(UserIdentity identity) {
                this.userIdentity = identity;
        }
        
        public UserIdentity getUserIdentity() {
                return userIdentity;
        }
        
        public void setUserIdentity(UserIdentity userIdentity) {
                this.userIdentity = userIdentity;
        }
        
        @JsonIgnore
        @Override
        public void validate() throws InvalidRequestException {
                if (userIdentity.getUid() == null || userIdentity.getUid().isEmpty()) {
			throw new InvalidRequestException("User id is invalid");
		}
        }
}
