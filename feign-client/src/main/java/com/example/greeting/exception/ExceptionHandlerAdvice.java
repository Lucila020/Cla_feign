package com.example.greeting.exception;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.slf4j.LoggerFactory.getLogger;

@RestControllerAdvice
class ExceptionHandlerAdvice {

  private static final Logger LOGGER = getLogger(ExceptionHandlerAdvice.class.getName());

  @ExceptionHandler(InvalidGreetingException.class)
  public ResponseEntity<InvalidGreetingException> handleException(InvalidGreetingException e) {
    LOGGER.error("Error while receiving message: ", e);
    return new ResponseEntity<>(e, e.getHttpStatus());
  }
}
