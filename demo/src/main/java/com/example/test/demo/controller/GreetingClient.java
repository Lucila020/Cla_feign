package com.example.test.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/greeting")
public interface GreetingClient {

    /*@GetMapping(value = "/get-greeting", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Greeting> greeting(@RequestParam(name = "message") String msg);*/

}