
package com.sanoxy.dao.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanoxy.service.util.Permission;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "user_join_workspace",
       uniqueConstraints = {
                @UniqueConstraint(columnNames = {"uid", "wid"})
       }
)
public class UserJoinWorkspace implements Serializable {
        
        Integer uwid;
        User user;
        Workspace workspace;
        private String permissionsJson;
        ObjectMapper mapper = new ObjectMapper();

        public UserJoinWorkspace() {
        }
        
        public UserJoinWorkspace(User user, Workspace workspace, Set<Permission> permissions) throws JsonProcessingException {
                this.user = user;
                this.workspace = workspace;
                this.permissionsJson = mapper.writeValueAsString(permissions);
        }
        
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "uwid")
        public Integer getUwid() {
                return this.uwid;
        }
        
        public void setUwid(Integer uwid) {
                this.uwid = uwid;
        }
        
        @ManyToOne(targetEntity=User.class)
        @JoinColumn(name = "uid")
        @NotNull
        public User getUser() {
                return this.user;
        }
        
        public void setUser(User user) {
                this.user = user;
        }
        
        @ManyToOne(targetEntity=Workspace.class)
        @JoinColumn(name = "wid")
        @NotNull
        public Workspace getWorkspace() {
                return this.workspace;
        }
        
        public void setWorkspace(Workspace workspace) {
                this.workspace = workspace;
        }
        
        @NotNull
        @NotEmpty
        @Lob
        public String getPermissionsJson() {
                return this.permissionsJson;
        }
        
        public void setPermissionsJson(String permissionsJson) {
                this.permissionsJson = permissionsJson;
        }
        
        @Transient
        private ObjectMapper getMapper() {
                return this.mapper;
        }

        @Transient
        public Set<Permission> getPermissions() {
                try {
                        return mapper.readValue(this.permissionsJson, new TypeReference<Set<Permission>>() {});
                } catch (IOException ex) {
                        Logger.getLogger(UserJoinWorkspace.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
        }
}
