
package com.sanoxy.controller;

import com.sanoxy.controller.response.Response;
import com.sanoxy.controller.response.Response.Status;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "api/workspace", produces = {MediaType.APPLICATION_JSON_VALUE})
public class WorkspaceController {
        
        @RequestMapping(value = {"/connection/test", ""}, method = RequestMethod.GET)
        @ResponseBody
        public Response validateConnection() {
                return new Response(Status.Success);
        }
}
