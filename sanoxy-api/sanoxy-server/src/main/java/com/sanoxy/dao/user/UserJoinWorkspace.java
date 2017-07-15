
package com.sanoxy.dao.user;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author davis
 */
@Entity
@Table(name = "user_join_workspace")
public class UserJoinWorkspace implements Serializable {
        
        @Id
        Integer uwid;
        
        @ManyToOne(targetEntity=User.class)
        @JoinColumn(name = "uid")
        User user;
        
        @ManyToOne(targetEntity=Workspace.class)
        @JoinColumn(name = "wid")
        Workspace workspace;
        
        @NotNull
        @NotEmpty
        private String permissionsJson;

        public UserJoinWorkspace() {
        }
        
        public UserJoinWorkspace(User user, Workspace workspace, String permissionsJson) {
                this.user = user;
                this.workspace = workspace;
                this.permissionsJson = permissionsJson;
        }
        
        public Integer getUwid() {
                return this.uwid;
        }
        
        public void setUwid(Integer uwid) {
                this.uwid = uwid;
        }
        
        public User getUser() {
                return this.user;
        }
        
        public void setUser(User user) {
                this.user = user;
        }
        
        public Workspace getWorkspace() {
                return this.workspace;
        }
        
        public void setWorkspace(Workspace workspace) {
                this.workspace = workspace;
        }
        
        public String getPermissionsJson() {
                return this.permissionsJson;
        }
        
        public void setPermissionsJson(String permissionsJson) {
                this.permissionsJson = permissionsJson;
        }
}
