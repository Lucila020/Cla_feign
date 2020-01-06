package com.example.test.demo.exception;

public class InvalidRequestException extends BaseApiException {

    public InvalidRequestException(String message) {
        super(ExceptionErrorCode.DEMO_ERROR_02, message);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
