package com.example.test.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
