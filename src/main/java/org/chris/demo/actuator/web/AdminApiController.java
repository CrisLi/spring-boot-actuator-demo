package org.chris.demo.actuator.web;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminApiController {

    private final MeterRegistry meterRegistry;

    private Counter counter;

    @PostConstruct
    public void init() {
        this.counter = meterRegistry
            .counter("myadminapi", "rest_api", "true");
    }

    @GetMapping("/admin/api")
    public String works() {
        counter.increment();
        return "admin api works";
    }

}
