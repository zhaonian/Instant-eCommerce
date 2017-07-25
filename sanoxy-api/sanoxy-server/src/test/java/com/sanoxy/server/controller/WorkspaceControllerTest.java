
package com.sanoxy.server.controller;

import com.sanoxy.configuration.ControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class WorkspaceControllerTest extends ControllerTest {
        
        @Test
        public void createWorkspaceTest() {
        }
        
        @Test
        public void userWorkspaceTest() {
        }
}
