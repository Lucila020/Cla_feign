package com.example.test.demo.service;

import com.example.test.demo.dto.Greeting;
import com.example.test.demo.dto.UserDTO;
import com.example.test.demo.exception.InvalidRequestException;
import com.example.test.demo.exception.NoDataFoundException;
import com.example.test.demo.sto.UserSTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompositionService {

    ResponseEntity<Greeting> getGreeting(String msg);

    boolean userExist(String email);

    List<UserSTO> getUsers(String email);

    String loginUser(String email, String password) throws NoDataFoundException;

    String readToken(String token);

    UserSTO saveUser(UserDTO userDTO);

    void deleteUser(String email) throws InvalidRequestException;
}
