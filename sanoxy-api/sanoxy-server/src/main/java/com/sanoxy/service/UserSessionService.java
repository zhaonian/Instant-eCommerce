
package com.sanoxy.service;

import com.sanoxy.dao.user.User;

/**
 * @author davis
 */
public interface UserSessionService {
        
        public User getUser(String uuid);
        
        public void setUser(String uuid, User u);
        
        public void removeUser(String uuid);
}
