
package com.sanoxy.controller.response;


public class ServerInfoResponse extends Response {
        
        String versionString;
        
        public ServerInfoResponse() {
        }
        
        public ServerInfoResponse(Status status, String versionString) {
                super(status);
                this.versionString = versionString;
        }
        
        public String getVersionString() {
                return this.versionString;
        }
        
        public void setVersionString(String versionString) {
                this.versionString = versionString;
        }
}
