package com.example.test.demo.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomApiExceptionSerializer extends StdSerializer<BaseApiException> {

    public CustomApiExceptionSerializer() {
        super(BaseApiException.class);
    }

    public void serialize(
            BaseApiException exception, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeRaw(exception.toString());
        jgen.close();
    }
}
