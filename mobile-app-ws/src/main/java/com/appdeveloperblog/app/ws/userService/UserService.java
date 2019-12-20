package com.appdeveloperblog.app.ws.userService;

import com.appdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appdeveloperblog.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appdeveloperblog.app.ws.ui.model.respose.UserRest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


public interface UserService {

   UserRest createUser(UserDetailsRequestModel userDetailsRequestModel);
   UserRest getUsers(String id) throws UserServiceException;
   void setUsers(String id,UserRest userRest);
   void removeUsers(String id) throws UserServiceException;
}
