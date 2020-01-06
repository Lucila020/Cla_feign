package com.example.test.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionErrorCode {
  DEMO_ERROR_01(HttpStatus.INTERNAL_SERVER_ERROR),
  DEMO_ERROR_02(HttpStatus.BAD_REQUEST),
  DEMO_ERROR_03(HttpStatus.NOT_FOUND),
  DEMO_ERROR_04(HttpStatus.CONFLICT);

  private HttpStatus httpStatus;

  ExceptionErrorCode(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }
}
