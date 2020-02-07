package org.chris.demo.actuator.endpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.Builder;
import lombok.Value;

@Component
@RestControllerEndpoint(id = "myrestcontrollerendpoint")
public class MyRestControllerEndpoint {

    private Map<String, Feature> features = new ConcurrentHashMap<>();

    @GetMapping
    public Map<String, Feature> features() {
        return features;
    }

    @GetMapping("/{name}")
    public Feature feature(@PathVariable String name) {
        return features.get(name);
    }

    @PostMapping("/{name}")
    public void configureFeature(@PathVariable String name, @RequestBody Feature feature) {
        features.put(name, feature);
    }

    @DeleteMapping("/{name}")
    public void deleteFeature(String name) {
        features.remove(name);
    }

    @Value
    @Builder
    public static class Feature {

        private String name;
        private boolean enabled;

    }
}
