package com.example.greeting.exception;

import lombok.Getter;

import static com.example.greeting.utils.GsonSerializer.GSON;

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
    return GSON.toJson(this);
  }
}
