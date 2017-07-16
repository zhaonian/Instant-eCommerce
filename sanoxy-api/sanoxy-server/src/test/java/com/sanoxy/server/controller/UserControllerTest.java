/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanoxy.server.controller;

import com.sanoxy.controller.response.UserIdentityResponse;
import com.sanoxy.dao.user.User;
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

/**
 *
 * @author luan
 */
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
                UserIdentityResponse iid = requestNewUserLogin();
                
                User user = getRequestedNewUser();
                User loggedInUser = userSessionService.getIdentityInfo(iid.getUserIdentity().getUid()).getUser();
                assertEquals(loggedInUser, user);
        }

        @Test
        @Rollback
        public void userLogIOutest() throws Exception {
                requestNewUser();
                UserIdentityResponse iid = requestNewUserLogin();
                requestUserLogout(iid);
                
                assertNull(userSessionService.getIdentityInfo(iid.getUserIdentity().getUid()));
        }

        @Test
        public void validateConnectionTest() throws Exception {
                /*mockMvc.perform(get("/api/user/connection/sanoxy"))
                        .andExpect(jsonPath("$.dbid", is("sanoxy")));*/
        }
}
