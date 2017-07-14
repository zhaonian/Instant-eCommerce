package com.sanoxy.controller.response;

import java.util.Objects;
import java.util.UUID;

public class Identity {
	
	private String Id;
	
        public Identity() {
	}
        
	public Identity(boolean uuid) {
		this.Id = UUID.randomUUID().toString();
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
        
        @Override
        public boolean equals(Object o) {
                if (!(o instanceof Identity))
                        return false;
                Identity rhs = (Identity) o;
                return Id.equals(rhs.Id);
        }

        @Override
        public int hashCode() {
                int hash = 7;
                hash = 97 * hash + Objects.hashCode(this.Id);
                return hash;
        }
}
