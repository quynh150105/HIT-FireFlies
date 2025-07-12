package com.example.hit_networking_base.repository;

import com.example.hit_networking_base.domain.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Integer> {
    List<JobPost> findByDeletedAtIsNullOrderByCreatedAtDesc();
}
