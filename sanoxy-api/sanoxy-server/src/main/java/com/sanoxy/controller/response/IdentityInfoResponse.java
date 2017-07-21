
package com.sanoxy.controller.response;

import com.sanoxy.service.util.IdentityInfo;


public class IdentityInfoResponse extends Response {
        
        IdentityInfo identiyInfo;
        
        public IdentityInfoResponse() {
        }
        
        public IdentityInfoResponse(IdentityInfo identityInfo) {
                this.identiyInfo = identityInfo;
        }
        
        public IdentityInfo getIdentityInfo() {
                return this.identiyInfo;
        }
        
        public void setIdentityInfo(IdentityInfo identityInfo) {
                this.identiyInfo = identityInfo;
        }
}
