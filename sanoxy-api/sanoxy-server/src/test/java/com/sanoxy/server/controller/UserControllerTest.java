/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanoxy.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanoxy.configuration.ControllerTest;
import com.sanoxy.controller.request.user.CreateUserRequest;
import com.sanoxy.controller.request.user.LogInRequest;
import com.sanoxy.controller.request.user.LogoutRequest;
import com.sanoxy.controller.response.UserIdentityResponse;
import com.sanoxy.dao.user.User;
import com.sanoxy.repository.user.UserRepository;
import com.sanoxy.service.IdentitySessionService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author luan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class UserControllerTest extends ControllerTest {

        @Autowired
        private UserRepository userRepository;
        
        @Autowired
        private IdentitySessionService userSessionService;
        
        private UserIdentityResponse responseToIdentity(MvcResult result) throws Exception {
                String content = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                UserIdentityResponse iid = mapper.readValue(content, UserIdentityResponse.class);
                return iid;
        }
        
        private void requestNewUser() throws Exception {
                CreateUserRequest request = new CreateUserRequest();
                request.setUsername("test-user");
                request.setPassword("test-password");
                mockMvc.perform(post("/api/user/create")
                        .content(json(request))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk());
        }
        
        private UserIdentityResponse requestNewUserLogin() throws Exception {
                LogInRequest logInRequest = new LogInRequest("test-user", "test-password");
                MvcResult result = mockMvc.perform(post("/api/user/login/imaginarydb")
                        .content(json(logInRequest))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk())
                        .andReturn();
                
                return responseToIdentity(result);
        }
        
        private void requestUserLogout(UserIdentityResponse iid) throws Exception {
                LogoutRequest request = new LogoutRequest(iid.getUserIdentity());
                mockMvc.perform(post("/api/user/logout")
                        .content(json(request))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk());
        }
        
        private User getRequestedNewUser() throws Exception {
                return userRepository.findByName("test-user");
        }

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
