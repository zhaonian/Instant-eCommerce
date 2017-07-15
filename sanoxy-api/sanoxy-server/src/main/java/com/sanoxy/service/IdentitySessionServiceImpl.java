package com.sanoxy.service;

import com.sanoxy.service.util.IdentityInfo;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class IdentitySessionServiceImpl implements IdentitySessionService {
	
        private final Map<String, IdentityInfo> identities = new HashMap();
        
        @Override
        public IdentityInfo getIdentityInfo(String uuid) {
                return identities.get(uuid);
        }
        
        @Override
        public void putIdentityInfo(String uuid, IdentityInfo info) {
                identities.put(uuid, info);
        }
        
        @Override
        public void removeIdentityInfo(String uuid) {
                if (identities.containsKey(uuid)) {
                        identities.remove(uuid);
                }
        }
}
