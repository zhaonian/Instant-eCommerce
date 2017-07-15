
package com.sanoxy.service.util;

/**
 * @author davis
 */
public class DatabaseConnection {
        
        private Integer dbid;
	
	public DatabaseConnection() { };
	
	public DatabaseConnection(Integer dbid) {
                this.dbid = dbid;
	}
        
        public Integer getDbid() {
                return dbid;
        }
        
        public void setDbid(Integer dbid) {
                this.dbid = dbid;
        }
}
