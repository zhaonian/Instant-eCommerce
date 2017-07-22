
package com.sanoxy.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanoxy.configuration.ControllerTest;
import com.sanoxy.controller.request.user.CreateUserRequest;
import com.sanoxy.controller.request.user.LogInRequest;
import com.sanoxy.controller.request.user.LogoutRequest;
import com.sanoxy.dao.user.User;
import com.sanoxy.repository.user.UserRepository;
import com.sanoxy.service.IdentitySessionService;
import com.sanoxy.service.util.UserIdentity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class SanoxyControllerTest extends ControllerTest {
        
        @Autowired
        protected UserRepository userRepository;
        
        @Autowired
        protected IdentitySessionService userSessionService;
        
        protected UserIdentity responseToIdentity(MvcResult result) throws Exception {
                String content = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                UserIdentity iid = mapper.readValue(content, UserIdentity.class);
                return iid;
        }
        
        protected void requestNewUser() throws Exception {
                CreateUserRequest request = new CreateUserRequest();
                request.setUsername("test-user");
                request.setPassword("test-password");
                mockMvc.perform(post("/api/user/create")
                        .content(json(request))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk());
        }
        
        protected UserIdentity requestNewUserLogin() throws Exception {
                LogInRequest logInRequest = new LogInRequest("test-user", "test-password");
                MvcResult result = mockMvc.perform(post("/api/user/login/imaginarydb")
                        .content(json(logInRequest))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk())
                        .andReturn();
                
                return responseToIdentity(result);
        }
        
        protected void requestUserLogout(UserIdentity iid) throws Exception {
                LogoutRequest request = new LogoutRequest(iid);
                mockMvc.perform(post("/api/user/logout")
                        .content(json(request))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk());
        }
        
        protected User getRequestedNewUser() throws Exception {
                return userRepository.findByName("test-user");
        }
        
        @Test
        public void validateConnectionTest() throws Exception {
                mockMvc.perform(get("/api/test_connection"))
                        .andExpect(status().isOk());
        }
}
