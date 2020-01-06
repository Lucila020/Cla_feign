package com.example.test.demo.exception;

public class NoDataFoundException extends BaseApiException {

    public NoDataFoundException(String message) {
        super(ExceptionErrorCode.DEMO_ERROR_03, message);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
