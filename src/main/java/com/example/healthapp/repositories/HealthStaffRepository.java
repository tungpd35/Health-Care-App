package com.example.healthapp.repositories;

import com.example.healthapp.models.HealthStaff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthStaffRepository extends JpaRepository<HealthStaff, Long> {
    HealthStaff findHealthStaffByEmail(String email);
}
