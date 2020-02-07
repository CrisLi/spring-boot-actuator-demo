package org.chris.demo.actuator.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api")
public class AdminApiController {

    @GetMapping
    public String works() {
        return "admin api works";
    }

}
