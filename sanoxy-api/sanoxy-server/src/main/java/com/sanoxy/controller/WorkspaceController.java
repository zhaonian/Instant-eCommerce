
package com.sanoxy.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "api/workspace", produces = {MediaType.APPLICATION_JSON_VALUE})
public class WorkspaceController {
}
