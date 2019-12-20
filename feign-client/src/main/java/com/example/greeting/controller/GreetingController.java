package com.example.greeting.controller;

import com.example.greeting.dto.GreetingDTO;
import com.example.greeting.exception.ExceptionErrorCode;
import com.example.greeting.exception.InvalidGreetingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  @GetMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GreetingDTO> greeting(@RequestParam(name = "name") String name)
      throws InvalidGreetingException {
    if (!"Federico".equals(name)) {
      throw new InvalidGreetingException(
          ExceptionErrorCode.GREETING_ERROR_02, "name value is invalid");
    }
    return new ResponseEntity<>(
        GreetingDTO.builder().msg("Hola " + name + "!").build(), HttpStatus.OK);
  }
}
