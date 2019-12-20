package com.example.photoappApi.controller;

import com.example.photoappApi.data.AlbumEntity;
import com.example.photoappApi.model.AlbumResponseModel;
import com.example.photoappApi.service.AlbumsService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users/{id}/albums")
public class AlbumsController {

    @Autowired
    AlbumsService albumsService;

    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
            })
    public List<AlbumResponseModel> userAlbums(@PathVariable String id){

         List<AlbumResponseModel> returnValue = new ArrayList<>();

         List<AlbumEntity> albumsEntities = albumsService.getAlbums(id);

         if(albumsEntities == null || albumsEntities.isEmpty()){

             return returnValue;
         }

        Type listType = new TypeToken<List<AlbumResponseModel>>(){}.getType();

        returnValue = new ModelMapper().map(albumsEntities,listType);

        return returnValue;

    }
}
