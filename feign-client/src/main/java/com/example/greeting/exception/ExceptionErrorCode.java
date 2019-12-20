package com.example.greeting.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionErrorCode {
  GREETING_ERROR_01(HttpStatus.INTERNAL_SERVER_ERROR),
  GREETING_ERROR_02(HttpStatus.BAD_REQUEST),
  GREETING_ERROR_03(HttpStatus.NOT_FOUND);

  private HttpStatus httpStatus;

  ExceptionErrorCode(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }
}
