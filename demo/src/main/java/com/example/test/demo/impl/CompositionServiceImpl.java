package com.example.test.demo.impl;

import com.example.test.demo.dto.Greeting;
import com.example.test.demo.dto.UserDTO;
import com.example.test.demo.exception.InvalidRequestException;
import com.example.test.demo.exception.NoDataFoundException;
import com.example.test.demo.repository.RoleRepository;
import com.example.test.demo.repository.UserRepository;
import com.example.test.demo.service.CompositionService;
import com.example.test.demo.service.DomainService;
import com.example.test.demo.service.TokenProvider;
import com.example.test.demo.sto.DomainGreetingSTO;
import com.example.test.demo.sto.UserSTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.test.demo.utils.Serializer.serialize;

@Service
public class CompositionServiceImpl implements CompositionService {

    private DomainService domainService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private TokenProvider tokenProvider;

    @Autowired
    public CompositionServiceImpl(DomainService domainService, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.domainService = domainService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public ResponseEntity<Greeting> getGreeting(String msg) {
        ResponseEntity<DomainGreetingSTO> dre = domainService.domainGreeting(msg);
        return new ResponseEntity<>(Greeting.builder().message(dre.getBody().getMsg()).build(), dre.getStatusCode());
    }

    @Override
    public boolean userExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public List<UserSTO> getUsers(String email) {
        if (email == null || email.isEmpty()) {
            return userRepository.findAll();
        } else {
            return (List<UserSTO>) Collections.singleton(userRepository.findByEmail(email).orElseGet(null));
        }
    }

    @Override
    public String loginUser(String email, String password) throws NoDataFoundException {
        Optional<UserSTO> userSTO = userRepository.findByEmail(email);
        if ( userSTO.isPresent() && passwordEncoder.matches(password, userSTO.get().getPassword()) ) {
            Map<String, String> token = new HashMap<>();
            token.put("token", tokenProvider.generateToken(userSTO.get()));
            return serialize(token);
        } else {
            throw new  NoDataFoundException("User not found");
        }
    }

    @Override
    public String readToken(String token) {
        return tokenProvider.readToken(token);
    }

    @Override
    public UserSTO saveUser(UserDTO userDTO) {
        UserSTO user = new UserSTO();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setEnabled(true);
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));

        userRepository.saveAndFlush(user);
        return user;
    }

    @Override
    public void deleteUser(String email) throws InvalidRequestException {
        if (userRepository.findByEmail(email).isPresent()) {
            userRepository.deleteByEmail(email);
        } else {
            throw new InvalidRequestException("User does not exist");
        }
    }
}
