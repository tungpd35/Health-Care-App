package com.example.healthapp.services;

import com.example.healthapp.models.ImageData;
import com.example.healthapp.models.User;
import com.example.healthapp.repositories.ImageDataRepository;
import com.example.healthapp.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class StorageService {

    @Autowired
    private ImageDataRepository repository;

    public ImageData uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = repository.save(ImageData.builder()
                .name(UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')))
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
           return imageData;
        }
        return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images= ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
    public byte[] getImageByUser(User user) {
        ImageData dbImageData = user.getAvatar();
        byte[] images= ImageUtils.decompressImage(dbImageData.getImageData());
        return images;
    }
}