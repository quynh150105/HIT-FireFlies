package com.example.hit_networking_base.repository;

import com.example.hit_networking_base.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Page<User> findAll(Pageable pageable);
    Optional<User> findById(Integer id);

    User findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);

}
