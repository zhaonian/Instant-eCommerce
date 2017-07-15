package com.sanoxy.dao.user;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "users")
public class User implements Serializable {

        @ManyToMany
        private List<UserDatabase> userDatabases;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
        
	@NotNull
        @NotEmpty
        @Column(unique=true)
	private String name;
        
        @NotNull
        @NotEmpty
        private String permissionsJson;
        
        @NotNull
        @NotEmpty
        private String encryptedPasscode;
        
        
        public User() {
        }
        
        public User(String name, String permissionsJson, String encryptedPasscode) {
                this.name = name;
                this.permissionsJson = permissionsJson;
                this.encryptedPasscode = encryptedPasscode;
        }
	
	public Integer getId() {
		return id;
	}
        
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
        
	public void setName(String name) {
		this.name = name;
	}
        
        public String getPermissionsJson() {
                return this.permissionsJson;
        }
        
        public void setPermissionsJson(String permissionsJson) {
                this.permissionsJson = permissionsJson;
        }
        
        public String getEncryptedPasscode() {
                return this.encryptedPasscode;
        }
        
        public void setEncrpytedPasscode(String encryptedPasscode) {
                this.encryptedPasscode = encryptedPasscode;
        }
        
        public List<UserDatabase> getUserDatabases() {
                return this.userDatabases;
        }
        
        public void setUserDatabases(List<UserDatabase> userDatabases) {
                this.userDatabases = userDatabases;
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
                return id.equals(rhs.id);
        }
}
