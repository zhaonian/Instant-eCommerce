
package com.sanoxy.controller.response;

import com.sanoxy.service.util.ActuationIdentity;


public class ActuatedResponse extends Response {
        
        private ActuationIdentity actuationIdentity;
        
        public ActuatedResponse() {
        }
        
        public ActuatedResponse(Status status, ActuationIdentity actuationIdentity) {
                super(status);
                this.actuationIdentity = actuationIdentity;
        }
        
        public ActuationIdentity getActuationIdentity() {
                return this.actuationIdentity;
        }
        
        public void setActuationIdentity(ActuationIdentity actuationIdentity) {
                this.actuationIdentity = actuationIdentity;
        }
}
