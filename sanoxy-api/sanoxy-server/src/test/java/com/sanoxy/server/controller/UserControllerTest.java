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
import com.sanoxy.controller.response.Identity;
import com.sanoxy.dao.user.User;
import com.sanoxy.repository.user.UserRepository;
import com.sanoxy.service.Session;
import static org.hamcrest.Matchers.is;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        
        private Identity responseToIdentity(MvcResult result) throws Exception {
                String content = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                Identity iid = mapper.readValue(content, Identity.class);
                return iid;
        }
        
        private Identity requestNewUser() throws Exception {
                CreateUserRequest request = new CreateUserRequest();
                request.setUsername("test-user");
                request.setPassword("test-password");
                MvcResult result = mockMvc.perform(post("/api/user/create")
                        .content(json(request))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk())
                        .andReturn();
                
                return responseToIdentity(result);
        }
        
        private Identity requestNewUserLogin() throws Exception {
                LogInRequest logInRequest = new LogInRequest("test-user", "test-password");
                MvcResult result = mockMvc.perform(post("/api/user/login")
                        .content(json(logInRequest))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk())
                        //.andExpect(content().json(json(user)))
                        .andReturn();
                
                return responseToIdentity(result);
        }
        
        private void requestUserLogout(Identity iid) throws Exception {
                LogoutRequest request = new LogoutRequest(iid.getId());
                mockMvc.perform(post("/api/user/logout")
                        .content(json(request))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk());
        }
        
        private User getRequestedNewUser() throws Exception {
                return userRepository.findByNameAndPassword("test-user", "test-password");
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
                Identity iid = requestNewUserLogin();
                
                User user = getRequestedNewUser();
                User loggedInUser = Session.getUser(iid.getId());
                assertEquals(loggedInUser, user);
        }

        @Test
        @Rollback
        public void userLogIOutest() throws Exception {
                requestNewUser();
                Identity iid = requestNewUserLogin();
                requestUserLogout(iid);
                
                assertNull(Session.getUser(iid.getId()));
        }

        @Test
        public void validateConnectionTest() throws Exception {
                mockMvc.perform(get("/api/user/connection/sanoxy"))
                        .andExpect(jsonPath("$.dbName", is("sanoxy")));
        }
}
