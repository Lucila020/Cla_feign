package com.example.greeting.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GreetingDTO {

  private String msg;
}
