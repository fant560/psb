package com.example.nodes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class NodesApplication {

    public static void main(String[] args) {
        SpringApplication.run(NodesApplication.class, args);
    }

    @Configuration
    public class WebApplicationConfiguration {

        @Bean
        public RestTemplate restTemplate(){
            return new RestTemplate();
        }
    }
}
