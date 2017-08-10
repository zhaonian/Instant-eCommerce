package com.sanoxy.controller.request.user;

import com.sanoxy.controller.request.ValidatedIdentifiedRequest;
import com.sanoxy.service.util.UserIdentity;

public class LogoutRequest extends ValidatedIdentifiedRequest {
        
        public LogoutRequest() {
        }
        
        public LogoutRequest(UserIdentity identity) {
                super(identity);
        }
}
