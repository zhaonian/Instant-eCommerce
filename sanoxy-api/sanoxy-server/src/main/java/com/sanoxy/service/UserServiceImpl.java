
package com.sanoxy.service;

import com.sanoxy.dao.user.User;
import com.sanoxy.dao.user.UserDatabase;
import com.sanoxy.repository.user.UserDatabaseRepository;
import com.sanoxy.repository.user.UserRepository;
import com.sanoxy.service.exception.DuplicatedUserException;
import com.sanoxy.service.exception.UserNotExistException;
import com.sanoxy.service.util.UserIdentity;
import java.util.List;
import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author davis
 */
@Service
public class UserServiceImpl implements UserService {
        @Autowired
        private UserRepository userRepository;
        
        @Autowired
        private UserDatabaseRepository userDatabaseRepository;
        
        @Autowired
        private UserSessionService userSessionService;
        
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        
        @Override
        public void createNew(String userName, String passcode) throws DuplicatedUserException {
                if (userRepository.existsByName(userName))
                        throw new DuplicatedUserException();
                
                String encryptedPasscode = encoder.encode(passcode);
                
                User user = new User(userName, "[]", encryptedPasscode);
                userRepository.save(user);
        }
        
        private UserDatabase findDatabaseByName(List<UserDatabase> databases, String name) {
                if (databases == null)
                        return null;
                for (UserDatabase db: databases) {
                        if (db.getName().equals(name))
                                return db;
                }
                return null;
        }

        @Override
        public UserIdentity authenticate(String dbName, String userName, String passcode) throws UserNotExistException,
                                                                                                 AuthenticationException {
                User user = userRepository.findByName(userName);
                if (user == null)
                        throw new UserNotExistException();
                
                if (!encoder.matches(passcode, user.getEncryptedPasscode())) 
                        throw new AuthenticationException("Password does not match");
                
                UserIdentity identity;
                if (dbName.equals("imaginarydb")) {
                        identity = new UserIdentity(-1);
                } else {
                        UserDatabase database = findDatabaseByName(user.getUserDatabases(), dbName);
                        if (database == null)
                                throw new UserNotExistException("User " + userName + " is not part of " + dbName);
                        identity = new UserIdentity(database.getDbid());
                }
                
                userSessionService.setUser(identity.getUid(), user);
                return identity;
        }

        @Override
        public void logout(UserIdentity identity) {
                userSessionService.removeUser(identity.getUid());
        }
        
}
