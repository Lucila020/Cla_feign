package com.example.test.demo.service;

import com.example.test.demo.sto.UserSTO;

public interface TokenProvider {

    String generateToken(UserSTO userSTO);

    String readToken(String token);
}
