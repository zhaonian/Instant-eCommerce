package com.sanoxy.dao.user;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "Sanoxy_user")
public class User implements Serializable {
	
	@Id
	@GenericGenerator(name = "id-generator", strategy = "com.sanoxy.repository.ResourceIdGenerator")
	@GeneratedValue(generator = "id-generator")
	private String id;
        
	@NotNull
	private Integer permission;
        
	@NotNull
	private String name;
        
	@NotNull
	private String salt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getPermission() {
		return permission;
	}
	public void setPermission(Integer permission) {
		this.permission = permission;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}

        @Override
        public int hashCode() {
                int hash = 3;
                hash = 53 * hash + Objects.hashCode(this.id);
                return hash;
        }
        
        @Override
        public boolean equals(Object o) {
                if (!(o instanceof User))
                        return false;
                User rhs = (User) o;
                return id == rhs.id;
        }
}
