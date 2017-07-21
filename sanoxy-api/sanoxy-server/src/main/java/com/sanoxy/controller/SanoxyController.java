
package com.sanoxy.controller;

import com.sanoxy.controller.response.Response;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SanoxyController {
        
        @RequestMapping(value = {"/test_connection", ""}, method = RequestMethod.GET)
        @ResponseBody
        public Response testConnection() {
                return new Response(Response.Status.Success);
        }
}
