package com.appdeveloperblog.app.ws.userService.implementation;

import com.appdeveloperblog.app.ws.data.AlbumsServiceClient;
import com.appdeveloperblog.app.ws.dto.AlbumResponseModel;
import com.appdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appdeveloperblog.app.ws.shared.Utils;
import com.appdeveloperblog.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appdeveloperblog.app.ws.ui.model.respose.UserRest;
import com.appdeveloperblog.app.ws.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    Map<String, UserRest> users;
    Utils utils;
    AlbumsServiceClient albumsServiceClient;

    @Autowired
    public UserServiceImpl(Map<String, UserRest> users, Utils utils, AlbumsServiceClient albumsServiceClient) {
        this.users = users;
        this.utils = utils;
        this.albumsServiceClient = albumsServiceClient;
    }

    @Override
    public UserRest createUser(UserDetailsRequestModel userDetailsRequestModel) {

        UserRest returnValue =new UserRest();
        returnValue.setEmail(userDetailsRequestModel.getEmail());
        returnValue.setFirstname(userDetailsRequestModel.getFirstName());
        returnValue.setLastname(userDetailsRequestModel.getLastName());

        String userId = utils.generateUserId();
        returnValue.setUserId(userId);
        List<AlbumResponseModel> albumsList = albumsServiceClient.getAlbums(userId);
        returnValue.setAlbums(albumsList);

        if(users == null) users = new HashMap<>();
          users.put(userId,returnValue);

        return returnValue;
    }

    public void setUsers(String id,UserRest userRest){
            users.put(id,userRest);

    }

    public UserRest getUsers(String id) throws UserServiceException{

        if(users != null && users.containsKey(id)) {

            return users.get(id);
        }else
            throw  new UserServiceException("Mapa de usuarios  vacio");
    }

    public void removeUsers(String id) throws UserServiceException{
        if(users != null && users.containsKey(id))
             users.remove(id);
        else
            throw  new UserServiceException("No se pudo eliminar el usuario");
    }


}
