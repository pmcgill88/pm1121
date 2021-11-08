package com.example.tool.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;

public class ApplicationConfig {

    @Bean
    public static ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
