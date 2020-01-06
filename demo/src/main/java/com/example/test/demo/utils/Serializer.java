package com.example.test.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Serializer {

    private Serializer() {
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String serialize(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
