package com.sanoxy.dao.user;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "users")
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer uid;
        
	@NotNull
        @NotEmpty
        @Column(unique=true)
	private String name;
        
        @NotNull
        @NotEmpty
        private String encryptedPasscode;
        
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        List<UserJoinWorkspace> userJoinWorkspaces;
        
        public User() {
        }
        
        public User(String name, String encryptedPasscode) {
                this.name = name;
                this.encryptedPasscode = encryptedPasscode;
        }
	
	public Integer getUid() {
		return uid;
	}
        
	public void setUid(Integer id) {
		this.uid = id;
	}
	
	public String getName() {
		return name;
	}
        
	public void setName(String name) {
		this.name = name;
	}
        
        public String getEncryptedPasscode() {
                return this.encryptedPasscode;
        }
        
        public void setEncrpytedPasscode(String encryptedPasscode) {
                this.encryptedPasscode = encryptedPasscode;
        }
        
        public List<UserJoinWorkspace> getUserJoinWorkspaces() {
                return this.userJoinWorkspaces;
        }
        
        public void setUserJoinWorkspace(List<UserJoinWorkspace> userJoinWorkspaces) {
                this.userJoinWorkspaces = userJoinWorkspaces;
        }
        
        @Override
        public int hashCode() {
                int hash = 3;
                hash = 53 * hash + Objects.hashCode(this.uid);
                return hash;
        }
        
        @Override
        public boolean equals(Object o) {
                if (!(o instanceof User))
                        return false;
                User rhs = (User) o;
                return uid.equals(rhs.uid);
        }
}
