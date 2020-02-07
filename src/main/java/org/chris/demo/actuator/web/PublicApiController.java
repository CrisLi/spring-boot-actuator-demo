package org.chris.demo.actuator.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api")
public class PublicApiController {

    @GetMapping
    public String get() {
        return "public api works";
    }

}
