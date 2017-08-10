
package com.sanoxy.controller;

import com.sanoxy.configuration.Constants;
import com.sanoxy.controller.response.Response;
import com.sanoxy.controller.response.ServerInfoResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SanoxyController {
        
        @RequestMapping(value = {"/server_info", ""}, method = RequestMethod.GET)
        @ResponseBody
        public ServerInfoResponse testConnection() {
                return new ServerInfoResponse(Response.Status.Success, Constants.SERVER_VERSION);
        }
}
