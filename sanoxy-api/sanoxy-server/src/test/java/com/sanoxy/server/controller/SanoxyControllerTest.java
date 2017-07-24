
package com.sanoxy.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanoxy.configuration.ControllerTest;
import com.sanoxy.controller.request.ValidatedIdentifiedRequest;
import com.sanoxy.controller.request.user.CreateUserRequest;
import com.sanoxy.controller.request.user.LogInRequest;
import com.sanoxy.controller.request.user.LogoutRequest;
import com.sanoxy.dao.user.User;
import com.sanoxy.dao.user.Workspace;
import com.sanoxy.repository.user.UserRepository;
import com.sanoxy.service.IdentitySessionService;
import com.sanoxy.service.UserService;
import com.sanoxy.service.exception.ResourceMissingException;
import com.sanoxy.service.util.IdentityInfo;
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
        
        @Autowired
        protected UserService userService;
        
        protected UserIdentity responseToIdentity(MvcResult result) throws Exception {
                String content = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                UserIdentity iid = mapper.readValue(content, UserIdentity.class);
                return iid;
        }
        
        protected IdentityInfo responseToIdentityInfo(MvcResult result) throws Exception {
                String content = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                IdentityInfo iid = mapper.readValue(content, IdentityInfo.class);
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
        
        protected UserIdentity requestUserLogin(String userName, String password, String workspaceName) throws Exception {
                LogInRequest logInRequest = new LogInRequest(userName, password);
                MvcResult result = mockMvc.perform(post("/api/user/login/" + workspaceName)
                        .content(json(logInRequest))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk())
                        .andReturn();
                
                return responseToIdentity(result);
        }
        
        protected UserIdentity requestNewUserLogin() throws Exception {
                return requestUserLogin("test-user", "test-password", "imaginarydb");
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
        
        protected IdentityInfo requestNewRootUser(UserIdentity identity, String workspaceName) throws Exception {
                MvcResult result = mockMvc.perform(post("/api/workspace/create/" + workspaceName)
                        .content(json(new ValidatedIdentifiedRequest(identity)))
                        .contentType(MEDIA_TYPE))
                        .andExpect(status().isOk())
                        .andReturn();
                return responseToIdentityInfo(result);
        }
        
        protected Workspace getLoggedInWorkspace(UserIdentity identity) throws ResourceMissingException {
                Workspace workspace = userService.getIdentityInfo(identity).getWorkspace();
                if (workspace == null)
                        throw new ResourceMissingException("Workspace for the current login status does not exist.");
                return workspace;
        }
        
        @Test
        public void validateConnectionTest() throws Exception {
                mockMvc.perform(get("/api/test_connection"))
                        .andExpect(status().isOk());
        }
}
