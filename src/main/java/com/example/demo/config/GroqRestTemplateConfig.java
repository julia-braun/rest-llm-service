package com.example.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GroqRestTemplateConfig {

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Bean
    @Qualifier("groqRestTemplate")
    public RestTemplate groqRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + groqApiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
