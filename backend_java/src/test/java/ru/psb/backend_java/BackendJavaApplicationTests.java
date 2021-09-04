package ru.psb.backend_java;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.http.HttpHeaders;

import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class BackendJavaApplicationTests {

    @Test
    public void doApiTest() throws Exception {
        var restTemplate = new RestTemplate();
        var userPasswordString = "Niyammon:Niyammonh1h";
        var authHeader = new String(Base64.getEncoder().encode(userPasswordString.getBytes(StandardCharsets.UTF_8)));
        var objectMapper = new ObjectMapper();
        var url = "http://elib-hackathon.psb.netintel.ru/elib/api/service/documents";
        var headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + authHeader);
        System.out.println(headers.get("Authorization"));
        headers.add("accept", "*/*");
        // headers.add("Content-type", MediaType.MULTIPART_FORM_DATA_VALUE);
        headers.add("Content-type", MediaType.MULTIPART_FORM_DATA_VALUE);
        System.out.println(UUID.randomUUID());
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("attachments", "@C:\\Users\\admin\\Downloads\\Промсвязьбанк Датасет\\Спецификация и пример запроса API\\Инструкция по созданию запроса.docx");
        var requestMap = new HashMap<String, Object>();
        requestMap.put("documentNomeclatureId", "b96c0bd2-7480-423e-81a1-cf3f8cf50814");
        requestMap.put("inn", "1234567891");
        requestMap.put("unrecognized", false);
        var requestString = objectMapper.writeValueAsString(requestMap);
        System.out.println(requestString);
        map.add("createRequest", requestString);

        var request = new HttpEntity<>(map, headers);

        var request2 =
                RequestEntity.post(URI.create(url))
                        .headers(new HttpHeaders(headers))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(map);



        var response = restTemplate.exchange(request2, String.class);
        System.out.println(response.getBody());
    }

    @Test
    void contextLoads() {
    }

}
