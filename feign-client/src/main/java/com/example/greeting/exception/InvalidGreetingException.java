package com.example.greeting.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonSerialize(using = CustomApiExceptionSerializer.class)
@Getter
public class InvalidGreetingException extends Exception {

  private final ErrorDetails errorDetails;
  private final HttpStatus httpStatus;

  public InvalidGreetingException(ExceptionErrorCode exceptionErrorCode, String message) {
    super(message);
    this.errorDetails = new ErrorDetails(exceptionErrorCode.toString(), message);
    this.httpStatus = exceptionErrorCode.getHttpStatus();
  }

  @Override
  public String toString() {
    return errorDetails.toString();
  }
}
