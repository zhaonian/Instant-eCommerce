
package com.sanoxy.controller.service;

import java.util.Objects;
import java.util.UUID;

/**
 * @author davis
 */
public class UserIdentity extends DatabaseConnection {
        
        private String uid;
	
        public UserIdentity() {
	}
        
	public UserIdentity(Integer dbid) {
                super(dbid);
		this.uid = UUID.randomUUID().toString();
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
        
        @Override
        public boolean equals(Object o) {
                if (!(o instanceof UserIdentity))
                        return false;
                UserIdentity rhs = (UserIdentity) o;
                return uid.equals(rhs.uid);
        }

        @Override
        public int hashCode() {
                int hash = 7;
                hash = 97 * hash + Objects.hashCode(this.uid);
                return hash;
        }
}
