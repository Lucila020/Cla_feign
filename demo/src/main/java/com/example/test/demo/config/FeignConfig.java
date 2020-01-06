package com.example.test.demo.config;

import com.example.test.demo.exception.CustomErrorDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    /*@Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
*/
}
