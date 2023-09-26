package com.example.healthapp.repositories;

import com.example.healthapp.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Service findServiceById(Long id);
}
