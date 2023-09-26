package com.example.healthapp.repositories;

import com.example.healthapp.models.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageDataRepository extends JpaRepository<ImageData, Long> {

    Optional<ImageData> findByName(String fileName);
}
