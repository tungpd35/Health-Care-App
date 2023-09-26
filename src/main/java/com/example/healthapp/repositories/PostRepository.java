package com.example.healthapp.repositories;

import com.example.healthapp.models.HealthStaff;
import com.example.healthapp.models.Post;
import com.example.healthapp.models.Service;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post getPostById(Long id);
    List<Post> getAllByHealthStaff(HealthStaff healthStaff);
    Page<Post> findAllByOrderByDateDesc(PageRequest pageRequest);
    Page<Post> findAllByOrderByTotalRequestDesc(PageRequest pageRequest);
    @Query(value = "select * from post where service_id = :id", nativeQuery = true)
    Page<Post> findAllByServiceId(Long id, PageRequest pageRequest);
    @Query(value = "select * from post where service_id = :id order by post.date desc ", nativeQuery = true)
    Page<Post> findAllByServiceIdAndSortByDate(Long id, PageRequest pageRequest);
    @Query(value = "select * from post where service_id = :id order by post.total_request desc ", nativeQuery = true)
    Page<Post> findAllByServiceIdAndSortByPopular(Long id, PageRequest pageRequest);
    @Modifying
    @Transactional
    @Query(value = "delete from post where post.id= :postId and post.health_staff_id= :userId", nativeQuery = true)
    void deletePostByIdAndUserId(Long postId, Long userId);
    @Query(value = "select * from post where post.service_id like :serviceId and (select city from address where address.post_id = post.id) like :city", nativeQuery = true)
    List<Post> getAllByServiceIdAndCity(String serviceId, String city);
    @Query(value = "select * from post where (select city from address where address.post_id = post.id) like :city",nativeQuery = true)
    Page<Post> getAllByCity(String city, PageRequest pageRequest);
    @Query(value = "select * from post where (select city from address where address.post_id = post.id) like :city order by post.date desc ",nativeQuery = true)
    Page<Post> getAllByCitySortByDate(String city, PageRequest pageRequest);
    @Query(value = "select * from post where (select city from address where address.post_id = post.id) like :city order by post.total_request desc",nativeQuery = true)
    Page<Post> getAllByCitySortByPopular(String city, PageRequest pageRequest);
    @Query(value = "select * from post where post.service_id like :serviceId and (select city from address where address.post_id = post.id) like :city order by post.total_request desc ", nativeQuery = true)
    Page<Post> getAllByCityAndService(String serviceId,String city, PageRequest pageRequest);
    @Query(value = "select * from post where post.service_id like :serviceId and (select city from address where address.post_id = post.id) like :city order by post.date desc ", nativeQuery = true)
    Page<Post> getAllByCityAndServiceSortByDate(String serviceId,String city, PageRequest pageRequest);
    @Query("SELECT p FROM Post p WHERE " +
            "p.title LIKE CONCAT('%',:keyword, '%')"+
            "OR p.service.name like concat('%', :keyword, '%')")
    Page<Post> searchAllByKeyWord(String keyword, PageRequest pageRequest);
}
