package com.sanoxy.service;

import java.util.HashMap;
import java.util.Map;

import com.sanoxy.dao.user.User;

public class Session {
	
        private static final Map<String, User> UUID_USER_Map = new HashMap();
        
        public static User getUser(String uuid) {
                return UUID_USER_Map.get(uuid);
        }
        
        public static void setUser(String uuid, User u) {
                UUID_USER_Map.put(uuid, u);
        }
        
        public static void removeUser(String uuid) {
                if (UUID_USER_Map.containsKey(uuid)) {
                        UUID_USER_Map.remove(uuid);
                }
        }
}
