
package com.sanoxy.service.util;


public class WorkspaceConnection {
        
        private Integer dbid;
	
	public WorkspaceConnection() { };
	
	public WorkspaceConnection(Integer dbid) {
                this.dbid = dbid;
	}
        
        public Integer getDbid() {
                return dbid;
        }
        
        public void setDbid(Integer dbid) {
                this.dbid = dbid;
        }
}
