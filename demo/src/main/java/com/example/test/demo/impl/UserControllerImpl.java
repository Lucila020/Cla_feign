package com.example.test.demo.impl;

import com.example.test.demo.controller.UserController;
import com.example.test.demo.dto.UserDTO;
import com.example.test.demo.exception.BaseApiException;
import com.example.test.demo.exception.InvalidRequestException;
import com.example.test.demo.exception.NoDataFoundException;
import com.example.test.demo.service.CompositionService;
import com.example.test.demo.sto.UserSTO;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class UserControllerImpl implements UserController {

    private CompositionService compositionService;
    private static final Logger LOG = getLogger(UserControllerImpl.class.getName());
    private Tracer tracer;

    @Autowired
    public UserControllerImpl(CompositionServiceImpl compositionService, Tracer tracer) {
        this.compositionService = compositionService;
        this.tracer = tracer;
    }

    @Override
    public ResponseEntity<String> login(String email, String password) throws BaseApiException {
        return new ResponseEntity<>(compositionService.loginUser(email, password), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> readToken(String token) throws BaseApiException {
        return new ResponseEntity<>(compositionService.readToken(token), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUsers(String email) throws BaseApiException {
        List<UserSTO> userSTOList = compositionService.getUsers(email);
        if (userSTOList == null || userSTOList.isEmpty()) {
            throw new NoDataFoundException("No data found");
        }
        List<UserDTO> userDTOList = userSTOList.stream().map(u -> UserDTO.builder().email(u.getEmail()).firstName(u.getFirstName()).lastName(u.getLastName()).build()).collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserSTO> createUser(UserDTO userDTO) throws InvalidRequestException {
        if (compositionService.userExist(userDTO.getEmail())) {
            throw new InvalidRequestException
                    ("There is an account with that email adress: " + userDTO.getEmail());
        }
        return new ResponseEntity<>(compositionService.saveUser(userDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String email) throws InvalidRequestException {
        compositionService.deleteUser(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
