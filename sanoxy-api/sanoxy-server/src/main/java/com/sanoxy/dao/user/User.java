package com.sanoxy.dao.user;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanoxy.service.util.Permission;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
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
        
        @NotNull
        @NotEmpty
        private String userPermissionsJson;
        
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        List<UserJoinWorkspace> userJoinWorkspaces;
        
        @Transient
        ObjectMapper mapper = new ObjectMapper();
        
        public User() {
        }
        
        public User(String name, String encryptedPasscode, Set<Permission> userPermissions) throws JsonProcessingException {
                this.name = name;
                this.encryptedPasscode = encryptedPasscode;
                this.userPermissionsJson = mapper.writeValueAsString(userPermissions);
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

        @Transient
        public Set<Permission> getUserPermissions() {
                try {
                        return mapper.readValue(userPermissionsJson, new TypeReference<Set<Permission>>() {});
                } catch (IOException ex) {
                        Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
        }
}
