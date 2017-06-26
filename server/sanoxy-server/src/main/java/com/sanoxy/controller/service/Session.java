package com.sanoxy.controller.service;

import java.util.HashMap;
import java.util.Map;

import com.sanoxy.dao.user.User;

public class Session {
	
        private static Map<String, User> UUID_2_USER = new HashMap();
        
        public static void setUser(String uuid, User u)
        {
                UUID_2_USER.set(uuid, u);
        }
        
        public static void get_user(String uuid)
        {
                return UUID_2_USER.get(uuid);
        }
}
