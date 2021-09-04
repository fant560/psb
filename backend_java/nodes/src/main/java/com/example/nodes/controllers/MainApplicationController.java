package com.example.nodes.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@Slf4j
public class MainApplicationController {

    private final RestTemplate restTemplate;

    @Value("${text.recognition.url:http://localhost:5004/annotate_document}")
    private String textRecognition;

    @Value("${text.recognition.task.url:http://localhost:5004/tasks/}")
    private String taskStatus;

    @Value("${text.recognition.response:http://localhost:8081/api/response}")
    private String gatewayUrl;

    @RequestMapping(value = "/upload")
    public void upload(@RequestBody byte[] file, @RequestHeader("filename") String filename) throws Exception {
        log.info("Received file - {}, bytearraysize - {}", filename, file.length);

        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        var split = filename.split("\\.");
        var extension = split[split.length - 1];
        var tempFile = Files.createTempFile(filename, extension);
        Files.write(tempFile, file);
        params.add("file", new FileSystemResource(tempFile));

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(params);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URI.create(textRecognition),
                HttpMethod.POST,
                requestEntity,
                String.class);
        var responseText = new StringBuilder();
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode == HttpStatus.ACCEPTED) {
            String code = responseEntity.getBody();
            boolean loading = true;
            while (loading) {
                TimeUnit.SECONDS.sleep(30L);
                Map<String, Object> result = restTemplate.exchange(URI.create(taskStatus + code), HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {
                }).getBody();
                if (result.containsKey("task_status")) {
                    var status = (String) result.get("task_status");
                    if (status.equals("SUCCESS")) {
                        var taskResult = (Map) result.get("task_result");
                        taskResult.keySet().forEach(key -> {
                            List<Object> results = (List) taskResult.get(key);
                            results.forEach(res -> {
                                if (res instanceof Map) {
                                    var map = (Map) res;
                                    var text = map.get("text");
                                    responseText.append(text);
                                }
                            });
                        });
                        loading = false;
                    } else {
                        if (status.equals("FAILURE")) {
                            throw new IllegalStateException("Не удалось обработать документ " + filename);
                        }
                    }
                } else {
                    throw new IllegalStateException("Что-то пошло не так, нейросеть отдаёт что-то не то");
                }
            }
        }
        String textToProcess = responseText.toString();
        if (textToProcess.contains("0710001")) {
            String body = "Финансовое досье/2021/1 квартал/Бухгалтерская отчётность/" + filename;
            String nomenclatureId = "4f501f4a-c665-4cc8-9715-6ed26e7819f2";
            HttpHeaders headers = new HttpHeaders();
            headers.add("filename", filename);
            headers.add("nomenclatureId", nomenclatureId);
            RequestEntity request = new RequestEntity(body, headers, HttpMethod.POST, URI.create(gatewayUrl));
            restTemplate.exchange(request, Void.class);
            // TODO uncomment
           /* String regex = "(\\\\d{4}-\\\\d{2}-\\\\d{2})";
            var matcher = Pattern.compile(regex).matcher(textToProcess);
            if (matcher.find()){

            }*/
            return;
        }
        if (textToProcess.contains("Уставный капитал")) {
            String body = "Юридическое досье/Учредительные и иные внутренние документы/" + filename;
            String nomenclatureId = "33a37ce4-c6a9-4dad-8424-707abd47c125";
            HttpHeaders headers = new HttpHeaders();
            headers.add("filename", filename);
            headers.add("nomenclatureId", nomenclatureId);
            var request = new RequestEntity(body, headers, HttpMethod.POST, URI.create(gatewayUrl));
            restTemplate.exchange(request, Void.class);
            return;
        }
    }
}
