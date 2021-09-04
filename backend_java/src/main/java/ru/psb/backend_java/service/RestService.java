package ru.psb.backend_java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestService {

    private RestTemplate restTemplate;
    @Value("${nodes.node.1:http://localhost:8082/api/upload}")
    private String node1;
    @Value("${nodes.node.2:http://localhost:8083/api/upload}")
    private String node2;
    @Value("${nodes.node.3:http://localhost:8084/api/upload}")
    private String node3;

    private void process(String filename, byte[] file) {
    }

}
