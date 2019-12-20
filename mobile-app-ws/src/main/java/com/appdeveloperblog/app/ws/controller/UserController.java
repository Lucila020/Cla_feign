package com.appdeveloperblog.app.ws.controller;

import com.appdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appdeveloperblog.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appdeveloperblog.app.ws.ui.model.respose.UserRest;
import com.appdeveloperblog.app.ws.userService.UserService;
import com.appdeveloperblog.app.ws.userService.implementation.UserServiceImpl;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    Map<String, UserRest> users ;

    @Autowired
    UserService userService;

    @GetMapping("/status/check")
    public String status(){
        String working = "working";
        return working;
    }

    @GetMapping(path = "/{userId}",
            produces =
                    {MediaType.APPLICATION_XML_VALUE,
                     MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) throws UserServiceException{

            return  new ResponseEntity<>(userService.getUsers(userId), HttpStatus.OK);


    }

    @GetMapping
    public String getUser(@RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "limit",defaultValue = "50") int limit,
                          @RequestParam(value = "sort",defaultValue = "desc",required = false) String sort){

        return "get user was called page = " + page + "  and  limit = " + limit  + " and sort = " + sort;
    }


    @PostMapping(
            consumes =
             {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE},
            produces =
                    {MediaType.APPLICATION_XML_VALUE,
                            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRest>  createUser(@Valid @RequestBody UserDetailsRequestModel userDetailsRequestModel){

        UserRest returnValue = userService.createUser(userDetailsRequestModel);

        return new ResponseEntity<UserRest>(returnValue,HttpStatus.OK);
    }

    @PutMapping( path = "/{userId}", consumes =
            {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE},
            produces =
                    {MediaType.APPLICATION_XML_VALUE,
                            MediaType.APPLICATION_JSON_VALUE})
    public UserRest updateUser(@PathVariable String userId,@Valid @RequestBody UpdateUserDetailsRequestModel updateUserDetReqModel){

        UserRest userDetail = userService.getUsers(userId);
        userDetail.setFirstname(updateUserDetReqModel.getFirstName());
        userDetail.setLastname(updateUserDetReqModel.getLastName());

        userService.setUsers(userId,userDetail);

        return userDetail;

    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id)
    {
        userService.removeUsers(id);
        return ResponseEntity.noContent().build();

    }
}
