
package com.sanoxy.controller;

import com.sanoxy.controller.response.WorkspaceConnectionResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author davis
 */
@Controller
@RequestMapping(value = "api/workspace", produces = {MediaType.APPLICATION_JSON_VALUE})
public class WorkspaceController {
        
        @RequestMapping(value = {"/connection/{workspace}", ""}, method = RequestMethod.GET)
        @ResponseBody
        public WorkspaceConnectionResponse validateConnection(@PathVariable("workspace") String workspace) {
                return new WorkspaceConnectionResponse(0);
        }
}
