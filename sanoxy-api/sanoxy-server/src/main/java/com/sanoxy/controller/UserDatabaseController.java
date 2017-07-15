
package com.sanoxy.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author davis
 */
@Controller
@RequestMapping(value = "api/database", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserDatabaseController {
        
}
