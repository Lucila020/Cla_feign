package com.example.test.demo.controller;

import com.example.test.demo.dto.UserDTO;
import com.example.test.demo.exception.BaseApiException;
import com.example.test.demo.exception.InvalidRequestException;
import com.example.test.demo.sto.UserSTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/user")
public interface UserController {

    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) throws BaseApiException;

    @GetMapping(value = "/readToken", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> readToken(@RequestParam String token) throws BaseApiException;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<UserDTO>> getUsers(@RequestParam(required = false) String email) throws BaseApiException;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserSTO> createUser(@RequestBody UserDTO userDTO) throws InvalidRequestException;

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteUser(@PathVariable String email) throws InvalidRequestException;

}
