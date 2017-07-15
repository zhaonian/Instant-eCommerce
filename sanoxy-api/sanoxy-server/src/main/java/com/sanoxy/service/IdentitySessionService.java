
package com.sanoxy.service;

import com.sanoxy.service.util.IdentityInfo;

/**
 * @author davis
 */
public interface IdentitySessionService {
        
        public IdentityInfo getIdentityInfo(String uuid);
        
        public void putIdentityInfo(String uuid, IdentityInfo u);
        
        public void removeIdentityInfo(String uuid);
}
