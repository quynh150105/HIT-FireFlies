package com.example.hit_networking_base.repository;

import com.example.hit_networking_base.domain.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Integer> {
    List<JobPost> findByDeletedAtIsNullOrderByCreatedAtDesc();
}
