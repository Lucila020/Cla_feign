package com.example.test.demo.exception;

import lombok.Getter;

import static com.example.test.demo.utils.Serializer.serialize;

@Getter
public class ErrorDetails {

    private String code;
    private String message;

    public ErrorDetails(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return serialize(this);
    }
}
