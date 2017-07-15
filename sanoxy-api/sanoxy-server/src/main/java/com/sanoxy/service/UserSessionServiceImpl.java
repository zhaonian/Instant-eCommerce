package com.sanoxy.service;

import com.sanoxy.dao.user.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class UserSessionServiceImpl implements UserSessionService {
	
        private final Map<String, User> UUID_USERS = new HashMap();
        
        @Override
        public User getUser(String uuid) {
                return UUID_USERS.get(uuid);
        }
        
        @Override
        public void setUser(String uuid, User u) {
                UUID_USERS.put(uuid, u);
        }
        
        @Override
        public void removeUser(String uuid) {
                if (UUID_USERS.containsKey(uuid)) {
                        UUID_USERS.remove(uuid);
                }
        }
}
