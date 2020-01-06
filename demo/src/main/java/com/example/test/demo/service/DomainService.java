package com.example.test.demo.service;

import com.example.test.demo.sto.DomainGreetingSTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ExampleDomainService", url = "http://localhost:9050/v1/core/producer")
public interface DomainService {

    @GetMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DomainGreetingSTO> domainGreeting(@RequestParam(name = "name") String name);

}
