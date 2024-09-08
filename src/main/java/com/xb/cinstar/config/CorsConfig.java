package com.xb.cinstar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    public void addCorsMappings(CorsRegistry corsRegistry){
    corsRegistry.addMapping("/**").allowedOrigins("http://localhost:3000")
            .allowedHeaders("*")
            .allowedMethods("GET","POST","PUT","DELETE")
            .allowCredentials(true);
    }
}
