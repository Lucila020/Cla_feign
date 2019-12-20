package com.example.greeting.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomApiExceptionSerializer extends StdSerializer<InvalidGreetingException> {

  public CustomApiExceptionSerializer() {
    super(InvalidGreetingException.class);
  }

  public void serialize(
      InvalidGreetingException exception, JsonGenerator jgen, SerializerProvider provider)
      throws IOException {
    jgen.writeRaw(exception.toString());
    jgen.close();
  }
}
