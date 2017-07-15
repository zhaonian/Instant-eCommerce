
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
@Table(name = "workspace")
public class Workspace implements Serializable {
        
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        Integer wid;
        
        @NotNull
        @NotEmpty
        @Column(unique=true)
        String name;
        
        @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL)
        List<UserJoinWorkspace> userJoinWorkspaces;
        
        public Workspace() {
        }
        
        public Workspace(String name) {
                this.name = name;
        }
        
        public Integer getWid() {
                return this.wid;
        }
        
        public void setWid(Integer wid) {
                this.wid = wid;
        }
        
        public String getName() {
                return this.name;
        }
        
        public void setName(String name) {
                this.name = name;
        }
        
        public List<UserJoinWorkspace> getUserJoinWorkspaces() {
                return this.userJoinWorkspaces;
        }
        
        public void setUserJoinWorkspace(List<UserJoinWorkspace> userJoinWorkspaces) {
                this.userJoinWorkspaces = userJoinWorkspaces;
        }
        
        @Override
        public boolean equals(Object o) {
                if (!(o instanceof Workspace))
                        return false;
                Workspace rhs = (Workspace) o;
                return Objects.equals(wid, rhs.wid);
        }

        @Override
        public int hashCode() {
                int hash = 3;
                hash = 67 * hash + Objects.hashCode(this.wid);
                return hash;
        }
}
