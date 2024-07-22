package com.xb.cinstar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebfluxConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
