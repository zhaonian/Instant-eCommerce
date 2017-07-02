/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanoxy.server.controller;

import com.sanoxy.configuration.ControllerTest;
import com.sanoxy.controller.request.user.CreateUserRequest;
import com.sanoxy.controller.request.user.LogInRequest;
import com.sanoxy.dao.user.User;
import com.sanoxy.repository.user.UserRepository;
import com.sanoxy.service.Session;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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

        @Test
        @Rollback
        public void createUserTest() throws Exception {
                CreateUserRequest request = new CreateUserRequest();
                request.setUsername("test-user");
                request.setPassword("test-password");
                mockMvc.perform(post("/api/user/create")
                        .content(json(request))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk());
                User user = userRepository.findByNameAndPassword("test-user", "test-password");
                assertNotNull(user);
        }

        @Test
        @Rollback
        public void userLogInTest() throws Exception {
                // create a usr in db first
                User user = new User();
                user.setName("test-user");
                user.setSalt("test-password");
                user.setPermission(1);
                String id = userRepository.save(user).getId();

                // log in
                LogInRequest logInRequest = new LogInRequest("test-user", "test-password");
                mockMvc.perform(post("/api/user/login")
                        .content(json(logInRequest))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk());
                assertEquals(Session.getUser(id), user);
        }

        @Test
        @Rollback
        public void userLogIOutest() throws Exception {
                // create a usr in db first
                /*User user = new User();
                user.setName("test-user");
                user.setSalt("test-password");
                user.setPermission(1);
                String id = userRepository.save(user).getId();

                // log the user in
                LogInRequest logInRequest = new LogInRequest("test-user", "test-password");
                mockMvc.perform(post("/api/user/login")
                        .content(json(logInRequest))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk());
                assertEquals(Session.getUser(id), user);

                // log the user out
                LogoutRequest logOutRequest = new LogoutRequest();
                logOutRequest.setUsername("test-user");
                logOutRequest.setId(id);
                mockMvc.perform(post("/api/user/logout")
                        .content(json(logOutRequest))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk());
                assertNull(Session.getUser(id));*/
        }

        @Test
        public void validateConnectionTest() throws Exception {
                mockMvc.perform(get("/api/user/connection/sanoxy"))
                        .andExpect(jsonPath("$.dbName", is("sanoxy")));
        }
}
