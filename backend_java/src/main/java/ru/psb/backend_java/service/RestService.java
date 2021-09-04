package ru.psb.backend_java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RestService {

    private final RestTemplate restTemplate;
    // TODO несколько инстансов
    @Value("${nodes.node.1:http://localhost:8082/api/upload}")
    private String node1;
    @Value("${nodes.node.2:http://localhost:8082/api/upload}")
    private String node2;
    @Value("${nodes.node.3:http://localhost:8082/api/upload}")
    private String node3;

    public void process(String filename, byte[] file, Long documentId) {
        var hash = file.hashCode();
        var headers = new HttpHeaders();
        headers.put("filename", List.of(filename));
        headers.put("documentId", List.of(documentId.toString()));
        headers.put(HttpHeaders.CONTENT_TYPE, List.of("application/octet-stream"));
        var map = new HashMap<String, Object>();
        if (hash % 3 == 0) {
            var entity = new RequestEntity<>(file, headers, HttpMethod.POST, URI.create(node1));
            restTemplate.exchange(entity, Void.class);
        } else if (hash % 3 == 1) {
            var entity = new RequestEntity<>(file, headers, HttpMethod.POST, URI.create(node2));
            restTemplate.exchange(entity, Void.class);
        } else {
            var entity = new RequestEntity<>(file, headers, HttpMethod.POST, URI.create(node3));
            restTemplate.exchange(entity, Void.class);
        }
    }

}
