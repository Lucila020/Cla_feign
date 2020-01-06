package com.example.test.demo.exception;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.slf4j.LoggerFactory.getLogger;

@RestControllerAdvice
class ExceptionHandlerAdvice {

  private static final Logger LOGGER = getLogger(ExceptionHandlerAdvice.class.getName());

  @ExceptionHandler(InvalidRequestException.class)
  public ResponseEntity<InvalidRequestException> handleException(InvalidRequestException e) {
    LOGGER.error("Error while receiving message: ", e);
    return new ResponseEntity<>(e, e.getHttpStatus());
  }
}
