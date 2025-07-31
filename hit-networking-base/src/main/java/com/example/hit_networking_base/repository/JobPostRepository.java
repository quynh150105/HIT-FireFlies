package com.example.hit_networking_base.repository;

import com.example.hit_networking_base.domain.entity.JobPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Integer> {
   Page<JobPost> findByDeletedAtIsNull(Pageable pageable);
   Optional<JobPost> findByPostIdAndDeletedAtIsNull(Long postId);

   @Query("SELECT COUNT(j) FROM JobPost j WHERE j.creator.userId = :userId AND j.deletedAt IS NULL")
   Long countActivePostsByUserId(Long userId);

}
