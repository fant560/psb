package ru.psb.backend_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BackendJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendJavaApplication.class, args);
    }


    @Configuration
    public class WebConfiguration {
        @Bean
        public RestTemplate restTemplate(){
            return new RestTemplate();
        }
    }
}
