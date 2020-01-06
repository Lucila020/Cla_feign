package com.example.test.demo.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()) {
            case 400:
                return new BaseApiException(ExceptionErrorCode.DEMO_ERROR_02, "Invalid request");
            case 404:
                return new BaseApiException(ExceptionErrorCode.DEMO_ERROR_03, "Invalid request");
            default:
                return new BaseApiException(ExceptionErrorCode.DEMO_ERROR_01, "Invalid request");
        }
    }
}
