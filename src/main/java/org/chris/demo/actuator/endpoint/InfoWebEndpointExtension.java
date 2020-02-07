package org.chris.demo.actuator.endpoint;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@EndpointWebExtension(endpoint = InfoEndpoint.class)
@RequiredArgsConstructor
public class InfoWebEndpointExtension {

    private final InfoEndpoint delegate;

    @ReadOperation
    public WebEndpointResponse<Map<String, Object>> info() {

        Map<String, Object> info = this.delegate.info();
        Map<String, Object> result = new LinkedHashMap<>(info.size());

        result.putAll(info);
        result.put("hostname", System.getenv("HOSTNAME"));

        return new WebEndpointResponse<>(result);
    }

}
