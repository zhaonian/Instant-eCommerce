
package com.sanoxy.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sanoxy.controller.service.UserIdentity;
import com.sanoxy.controller.service.exception.InvalidRequestException;

/**
 * @author davis
 */
public abstract class ValidatedIdentifiedRequest implements ValidatedRequest {
        
        private UserIdentity identity;
        
        public ValidatedIdentifiedRequest() {
        }
        
        public ValidatedIdentifiedRequest(UserIdentity identity) {
                this.identity = identity;
        }
        
        public UserIdentity getIdentity() {
                return identity;
        }
        
        public void setIdentity(UserIdentity identity) {
                this.identity = identity;
        }
        
        @JsonIgnore
        @Override
        public void validate() throws InvalidRequestException {
                if (identity.getUid() == null || identity.getUid().isEmpty()) {
			throw new InvalidRequestException("User id is invalid");
		}
                if (identity.getDbid() == null) {
                        throw new InvalidRequestException("Database id is invalid");
                }
        }
}
