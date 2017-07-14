package com.sanoxy.service;

import java.util.HashMap;
import java.util.Map;

import com.sanoxy.dao.user.User;

public class Session {
	
        private static final Map<String, User> UUID_USERS = new HashMap();
        
        public static User getUser(String uuid) {
                return UUID_USERS.get(uuid);
        }
        
        public static void setUser(String uuid, User u) {
                UUID_USERS.put(uuid, u);
        }
        
        public static void removeUser(String uuid) {
                if (UUID_USERS.containsKey(uuid)) {
                        UUID_USERS.remove(uuid);
                }
        }
}
