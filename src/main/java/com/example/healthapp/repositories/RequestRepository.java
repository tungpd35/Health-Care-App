package com.example.healthapp.repositories;

import com.example.healthapp.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query(value = "select * from request where request.post_id in (select post.id from post where post.health_staff_id= :id) AND request.post_id in (select post_request.post_id from post_request)",nativeQuery = true)
    List<Request> findAllRequestByStaffId(Long id);
    Request findRequestById(Long id);
}
