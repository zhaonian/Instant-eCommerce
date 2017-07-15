
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
@Table(name = "user_database")
public class UserDatabase implements Serializable {
        
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        Integer dbid;
        
        @NotNull
        @NotEmpty
        @Column(unique=true)
        String name;
        
        @ManyToMany(mappedBy = "userDatabases")
        List<User> users;
        
        public UserDatabase() {
        }
        
        public UserDatabase(Integer dbid, String name) {
                this.dbid = dbid;
                this.name = name;
        }
        
        public Integer getDbid() {
                return this.dbid;
        }
        
        public void setDbid(Integer dbid) {
                this.dbid = dbid;
        }
        
        public String getName() {
                return this.name;
        }
        
        public void setName(String name) {
                this.name = name;
        }
        
        public List<User> getUsers() {
                return this.users;
        }
        
        public void setUsers(List<User> users) {
                this.users = users;
        }
        
        @Override
        public boolean equals(Object o) {
                if (!(o instanceof UserDatabase))
                        return false;
                UserDatabase rhs = (UserDatabase) o;
                return Objects.equals(dbid, rhs.dbid);
        }

        @Override
        public int hashCode() {
                int hash = 3;
                hash = 67 * hash + Objects.hashCode(this.dbid);
                return hash;
        }
}
