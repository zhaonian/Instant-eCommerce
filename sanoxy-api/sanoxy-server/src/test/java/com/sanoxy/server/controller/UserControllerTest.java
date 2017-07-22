
package com.sanoxy.server.controller;

import com.sanoxy.dao.user.User;
import com.sanoxy.service.util.UserIdentity;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class UserControllerTest extends SanoxyControllerTest {

        @Test
        @Rollback
        public void createUserTest() throws Exception {
                requestNewUser();
                User user = getRequestedNewUser();
                assertNotNull(user);
        }

        @Test
        @Rollback
        public void userLogInTest() throws Exception {
                requestNewUser();
                UserIdentity iid = requestNewUserLogin();
                
                User user = getRequestedNewUser();
                User loggedInUser = userSessionService.getIdentityInfo(iid.getUid()).getUser();
                assertEquals(loggedInUser, user);
        }

        @Test
        @Rollback
        public void userLogIOutest() throws Exception {
                requestNewUser();
                UserIdentity iid = requestNewUserLogin();
                requestUserLogout(iid);
                
                assertNull(userSessionService.getIdentityInfo(iid.getUid()));
        }
}
