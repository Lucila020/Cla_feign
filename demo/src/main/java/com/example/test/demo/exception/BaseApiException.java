package com.example.test.demo.exception;

import com.example.test.demo.utils.Serializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonSerialize(using = CustomApiExceptionSerializer.class)
@Getter
public class BaseApiException extends Exception {

    private final ErrorDetails errorDetails;
    private final HttpStatus httpStatus;

    public BaseApiException(ExceptionErrorCode exceptionErrorCode, String message) {
        super(message);
        this.errorDetails = new ErrorDetails(exceptionErrorCode.toString(), message);
        this.httpStatus = exceptionErrorCode.getHttpStatus();
    }

    @Override
    public String toString() {
        return Serializer.serialize(errorDetails);
    }
}
