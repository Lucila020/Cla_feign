package com.appdeveloperblog.app.ws.ui.model.respose;

import com.appdeveloperblog.app.ws.dto.AlbumResponseModel;

import java.util.List;

public class UserRest {

    private String firstname;
    private String lastname;
    private String email;
    private String userId;
    private List<AlbumResponseModel> albums;

    public UserRest() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public List<AlbumResponseModel> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumResponseModel> albums) {
        this.albums = albums;
    }

}
