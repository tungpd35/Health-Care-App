package com.example.healthapp.repositories;

import com.example.healthapp.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
