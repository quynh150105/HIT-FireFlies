package com.example.hit_networking_base.repository;

import com.example.hit_networking_base.domain.entity.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvRepository extends JpaRepository<CV,Long> {

    @Query("SELECT c FROM CV c WHERE c.jobPost.postId = :postId AND c.deletedAt IS NULL")
    List<CV> findByJobPostIdAndDeletedAtIsNull(Long postId);

    @Query("SELECT c FROM CV c WHERE c.user.userId = :userId AND c.deletedAt IS NULL")
    List<CV> findByUserIdAndDeletedAtIsNull(Long userId);
}
