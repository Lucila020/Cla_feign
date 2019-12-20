package com.example.photoappApi.service;

import com.example.photoappApi.data.AlbumEntity;

import java.util.List;

public interface AlbumsService {
    List<AlbumEntity> getAlbums(String userId);
}
